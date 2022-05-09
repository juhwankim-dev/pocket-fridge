"""
* db_pandas_test
* mariaDB 유저 식재료 데이터, 레시피 데이터 가져오기
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.04
* 마지막 수정일 2022.05.04
"""
from flask import Flask, request
from flask_restx import Resource, Api
import pymysql.cursors
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import mean_squared_error

# Flask 연동
app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask, Pandas, MariaDB API',
    description='Flask, Pandas, MariaDB 테스트를 위한 API'
)  # Swagger에서 보기 위함 : 앱 초기화, 앱 버전, 제목, 설명

# flask_restx의 api.namespace로 api 생성
namespace = api.namespace('flask', description='pandas 테스트')


@namespace.route('/get')
class TokenGet(Resource):
    @namespace.doc(responses={200: '레시피 정보 불러오기 완료'})
    @namespace.doc(responses={404: '레시피 정보 불러오기 실패'})
    def post(self):
        return {
            'message': '레시피 정보 불러오기 완료',
            'status': 200,
            'data': recipes
        }


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
# sql_recipe_like = "SELECT * from recipe right join recipe_like on user_id = %s and recipe.recipe_id = recipe_like.recipe_id"
sql_like = "SELECT * from recipe_like"  # 레시피 좋아요 sql 쿼리문

# 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
with conn:
    with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
        # 레시피 select 커서 실행
        cur.execute(sql_recipe)
        # ['recipe_id', 'recipe_all_ingredient', 'recipe_content', 'recipe_food_name', 'recipe_image', 'recipe_serving', 'recipe_time', 'recipe_type', 'recipe_food_summary']
        recipes = cur.fetchall()  # 모든 레시피 레코드 가져오기

        # 레시피 좋아요 select 커서 실행
        cur.execute(sql_like)  # 좋아요 레시피 가져오기
        likes = cur.fetchall()  # 모든 레시피 좋아요 레코드 가져오기

# 레시피 DataFrame
pd_recipes = pd.DataFrame(recipes)
with pd.option_context('display.max_rows', None, 'display.max_columns', None):  # DataFrame 깔끔하게 출력
    print(pd_recipes)

# 레시피 좋아요 DataFrame
pd_likes = pd.DataFrame(likes)
with pd.option_context('display.max_rows', None, 'display.max_columns', None):
    print(pd_likes)

# 레시피와 좋아요 데이터 결합
recipes_likes_merge = pd.merge(pd_recipes, pd_likes, on="recipe_id")
# print(recipes_likes_merge)

# 사용자-아이템 행렬 생성
matrix = pd_likes.pivot_table("recipe_like_id", "user_id", "recipe_id")

# NaN 값은 0으로 변환
matrix.fillna(0, inplace=True)
# with pd.option_context('display.max_rows', None, 'display.max_columns', None):
#     print(matrix)

# 아이템-사용자 행렬로 전치
matrix_T = matrix.T
with pd.option_context('display.max_rows', None, 'display.max_columns', None):
    print(matrix_T)

# 레시피 간 유사도 산출
# 아이템 유사도 행렬
# item_sim = cosine_similarity(matrix_T, matrix_T)

# 데이터 프레임 형태로 저장
# item_sim_df = pd.DataFrame(item_sim, index=matrix_T.index, columns=matrix_T.index)


# item_sim_df.shape: 3 x 3
# print(item_sim_df.iloc[:, :])


# 사용자별 예측 함수
# 인수로 사용자-아이템 행렬(NaN은 0으로 대체), 아이템 유사도 행렬 사용
def predict(likes_arr, recipes_arr):
    # likes_arr: u x i, recipes_arr: i x i
    sum_sr = likes_arr @ recipes_arr
    sum_s_abs = np.array([np.abs(recipes_arr).sum(axis=1)])

    pred = sum_sr / sum_s_abs

    return pred


# 사용자별 예측 (실제 평점이 없던 NaN값에 예측 평점 부여)
# likes_pred = predict(matrix.values, item_sim_df.values)

# likes_pred_matrix = pd.DataFrame(data=likes_pred, index=matrix.index,
#                                  columns=matrix.columns)

# with pd.option_context('display.max_rows', None, 'display.max_columns', None):
#     print(likes_pred_matrix)


# 예측 성능 평가 함수 (MSE 사용)
def get_mse(pred, actual):
    # 좋아요를 한 실제 레시피만 추출 (0값은 제외, 1차원 배열로 변환)
    pred = pred[actual.nonzero()].flatten()
    actual = actual[actual.nonzero()].flatten()
    print(f'pred: {pred}')
    print(f'actual: {actual}')

    return mean_squared_error(pred, actual)


# 아이템 기반 모든 인접 이웃 MSE
# print(f'아이템 기반 모든 인접 이웃 MSE: {get_mse(likes_pred, likes_pred_matrix.values):.4f}')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)  # localhost:80 접속 시 Swagger
