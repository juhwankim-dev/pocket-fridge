"""
* app.py
* 사용자 기반 레시피 추천
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.04
* 마지막 수정일 2022.05.13
"""
from flask import Flask, request
from flask_restx import Resource, Api
import pymysql.cursors
import numpy as np
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import mean_squared_error
import random
import jwt

# Flask 연동
app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask',
    description='레시피 추천을 위한 Swagger'
)  # Swagger에서 보기 위함 : 앱 초기화, 앱 버전, 제목, 설명

# flask_restx의 api.namespace로 api 생성
recommend_np = api.namespace('recommend', description='레시피 추천 API')

# jwt secret key 설정 (Spring jwt와 같은 secret key)
JWT_SECRET_KEY = 'SSAFYPocketFridge'
algorithm = 'HS256'


@recommend_np.route('')
class TokenGet(Resource):
    @recommend_np.doc(responses={200: '레시피 추천 완료'})
    @recommend_np.doc(responses={404: '레시피 추천 실패'})
    def get(self):
        # 1. JWT 토큰 인증
        header = request.headers.get('Authorization')  # Authorization 헤더로 담음
        if header is None:
            return {'message': 'Please Login'}, 404
        data = jwt.decode(header, JWT_SECRET_KEY, algorithms=algorithm)
        user_id = data.get('JWT')

        # 2. 추천 알고리즘
        recipes = recommend(user_id)

        return {
            'message': '레시피 추천 완료',
            'status': 200,
            'data': recipes
        }


def recommend(user_id):
    # DB 연동
    conn = pymysql.connect(
        host='k6d206.p.ssafy.io',
        port=3306,
        user='andback',
        password='Ssafy6!',
        db='pocket_fridge',
        charset='utf8',
        cursorclass=pymysql.cursors.DictCursor  # DB를 조회한 결과를 Column 명이 Key 인 Dictionary로 저장
    )

    sql_recipe = "SELECT * from recipe;"  # 레시피 sql 쿼리문
    sql_like = "SELECT * from recipe_like"  # 레시피 좋아요 sql 쿼리문

    # 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
    with conn:
        with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
            # 레시피 select 커서 실행
            cur.execute(sql_recipe)
            recipes = cur.fetchall()  # 모든 레시피 레코드 가져오기

            # 레시피 좋아요 select 커서 실행
            cur.execute(sql_like)  # 좋아요 레시피 가져오기
            likes = cur.fetchall()  # 모든 레시피 좋아요 레코드 가져오기

    # 레시피 DataFrame
    df_recipes = pd.DataFrame(recipes)

    # 레시피 좋아요 DataFrame
    df_likes = pd.DataFrame(likes)

    # 좋아요가 비어있으면, 랜덤 추천
    if df_likes.empty:
        df_dict = df_recipes.to_dict()  # DataFrame을 Dictionary로 변환
        dic_val = df_dict['recipe_id'].values()  # value 값만 리스트로 저장 후 API 응답
        dic_list = list(dic_val)
        random.shuffle(dic_list)  # 랜덤으로 섞기
        recipes = dic_list[:5]
    else:
        # 레시피와 좋아요 데이터 결합
        recipes_likes_merge = pd.merge(df_recipes, df_likes, on="recipe_id")

        # 사용자-아이템 행렬 생성
        likes_matrix = df_likes.pivot_table("recipe_like_id", "user_id", "recipe_id")

        # NaN 값은 0으로 변환 (inplace=True : 변경값으로 데이터프레임 저장함)
        likes_matrix.fillna(0, inplace=True)

        # 1.0 이상은 모두 1.0으로 변경 (좋아요는 1이므로)
        for i in likes_matrix.index:  # 인덱스
            for column in likes_matrix:  # 컬럼
                if likes_matrix[column][i] > 0.0:  # 0 이상이면 1로 변경(좋아요 체크)
                    likes_matrix[column][i] = 1.0

        # 아이템-사용자 행렬로 전치
        likes_matrix_T = likes_matrix.T
        with pd.option_context('display.max_rows', None, 'display.max_columns', None):
            print('<전치행렬>\n', likes_matrix_T)

        # 레시피 간 유사도 산출
        # 아이템 유사도 행렬 : 코사인 유사도 (좋아요에 따른 유사도이므로, 다른 종류의 레시피도 유사도가 비슷할수 있다)
        item_sim = cosine_similarity(likes_matrix_T, likes_matrix_T)

        # 데이터 프레임 형태로 저장
        item_sim_df = pd.DataFrame(item_sim, index=likes_matrix_T.index, columns=likes_matrix_T.index)


        # 사용자별 예측 함수
        # 인수로 사용자-아이템 행렬(NaN은 0으로 대체), 아이템 유사도 행렬 사용
        def predict(likes_arr, recipes_arr):
            # likes_arr: u x i, recipes_arr: i x i
            sum_sr = likes_arr @ recipes_arr  # matrix 곱연산 : @
            sum_s_abs = np.array([np.abs(recipes_arr).sum(axis=1)])

            pred = sum_sr / sum_s_abs

            return pred

        # 사용자별 좋아요 예측 (실제 평점이 없던 NaN값에 예측값 부여)
        likes_pred = predict(likes_matrix.values, item_sim_df.values)

        # 사용자별 좋아요 예측을 DataFrame으로 생성
        likes_pred_matrix = pd.DataFrame(data=likes_pred, index=likes_matrix.index,
                                         columns=likes_matrix.columns)

        with pd.option_context('display.max_rows', None, 'display.max_columns', None):
            print('<사용자별 좋아요 예측>\n', likes_pred_matrix)

        # 예측 성능 평가 함수 (MSE 사용)
        def get_mse(pred, actual):
            # 좋아요를 한 실제 레시피만 추출 (0값은 제외, 1차원 배열로 변환)
            pred = pred[actual.nonzero()].flatten()
            actual = actual[actual.nonzero()].flatten()
            print(f'pred: {pred}')
            print(f'actual: {actual}')

            return mean_squared_error(pred, actual)

        # 아이템 기반 모든 인접 이웃 MSE
        print(f'아이템 기반 모든 인접 이웃 MSE: {get_mse(likes_pred, likes_matrix.values):.4f}')

        def predict_likes_topsim(likes_arr, recipes_arr, N):

            # 사용자-아이템 행렬 크기만큼 0으로 채운 예측 행렬 초기화
            pred = np.zeros(likes_arr.shape)

            # 사용자-아이템 행렬의 열 크기(아이템 수)만큼 반복 (row: 사용자, col: 아이템)
            for col in range(likes_arr.shape[1]):
                # 특정 아이템의 유사도 행렬 오름차순 정렬시 index .. (1)
                temp = np.argsort(recipes_arr[:, col])

                # (1)의 index를 역순으로 나열시 상위 N개의 index = 특정 아이템의 유사도 상위 N개 아이템 index .. (2)
                top_n_recipes = [temp[:-1 - N:-1]]

                # 개인화된 예측 좋아요 계산: 반복당 특정 아이템의 예측 좋아요(사용자 전체)
                for row in range(likes_arr.shape[0]):
                    # (2)의 유사도 행렬
                    recipes_arr_topN = recipes_arr[col, :][top_n_recipes].T  # N x 1

                    # (2)의 실제 좋아요 점수 행렬
                    likes_arr_topN = likes_arr[row, :][top_n_recipes]  # 1 x N

                    # 예측 좋아요
                    pred[row, col] = likes_arr_topN @ recipes_arr_topN
                    pred[row, col] /= np.sum(np.abs(recipes_arr_topN))

            return pred

        # 사용자별 예측 좋아요 (matrix_T.shape[0] : 레시피개수)
        likes_pred = predict_likes_topsim(likes_matrix.values, item_sim_df.values, 5)

        # 성능 평가
        print(f'아이템 기반 인접 TOP-N 이웃 MSE: {get_mse(likes_pred, likes_matrix.values):.4f}')

        # 예측 좋아요 데이터 프레임
        likes_pred_matrix = pd.DataFrame(data=likes_pred, index=likes_matrix.index,
                                         columns=likes_matrix.columns)

        # 현재 user가 좋아요한 레시피
        user_likes_id = likes_matrix.loc[user_id, :]
        var = user_likes_id[user_likes_id > 0].sort_values(ascending=False)[:]
        print('<현재 유저가 좋아요한 레시피>', var)

        # 아직 좋아요를 누르지 않은 레시피 리스트 구하기
        def get_not_like_recipes(likes_matrix, user_id):

            # userId의 좋아요 정보
            user_likes = likes_matrix.loc[user_id, :]

            # 현재 유저가 아직 좋아요를 누르지 않은 레시피
            not_like_recipes_list = user_likes[user_likes == 0].index.tolist()

            # 모든 레시피를 list 객체로 만듬
            recipes_list = likes_matrix.columns.tolist()

            # 한줄 for + if문으로 좋아요를 안누른 레시피 리스트 생성
            not_like_list = [recipe for recipe in recipes_list if recipe in not_like_recipes_list]

            return not_like_list

        # 좋아요를 누르지 않은 레시피 중 예측 높은 순서로 시리즈 반환
        def recomm_recipe_by_userid(pred_df, user_id, not_like_list, N):
            recomm_recipes = pred_df.loc[user_id, not_like_list].sort_values(ascending=False)[:N]

            return recomm_recipes

        # 아직 좋아요를 누르지 않은 레시피 리스트
        not_like_list = get_not_like_recipes(likes_matrix, user_id)

        # 아이템 기반의 최근접 이웃 협업 필터링으로 레시피 추천
        recomm_recipes = recomm_recipe_by_userid(likes_pred_matrix, user_id, not_like_list, 5)
        recomm_recipes = pd.DataFrame(data=recomm_recipes.values, index=recomm_recipes.index,
                                      columns=['pred_score'])

        df_dict = recomm_recipes.to_dict()  # DataFrame을 Dictionary로 변환
        dic_val = df_dict['pred_score'].keys()  # value 값만 리스트로 저장 후 API 응답
        dic_list = list(dic_val)
        recipes = dic_list
        print(f'<현재 유저 {user_id} 에게 추천하는 레시피>', recipes)

        return recipes


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)  # localhost:80 접속 시 Swagger
