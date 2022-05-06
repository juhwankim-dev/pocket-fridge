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
import pandas as pd

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

sql = "SELECT * from recipe"  # sql 쿼리문

recipes_temp = []
recipes_pd = pd.DataFrame

# 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
with conn:
    with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
        cur.execute(sql)  # 커서로 sql 실행
        # ['recipe_id', 'recipe_all_ingredient', 'recipe_content', 'recipe_food_name', 'recipe_image', 'recipe_serving', 'recipe_time', 'recipe_type', 'recipe_food_summary']
        records = cur.fetchall()  # 모든 레코드 가져오기
        for record in records:  # 각 레코드 가져와서 출력
            print(record)
        recipes_temp.append(records)
        print(recipes_temp)

# Flask 연동
app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask, Pandas, MariaDB API',
    description='Flask, Pandas, MariaDB 테스트를 위한 API'
)  # Swagger에서 보기 위함 : 앱 초기화, 앱 버전, 제목, 설명

# flask_restx의 api.namespace로 api 생성
np = api.namespace('flask', description='pandas 테스트')


@np.route('/get')
class TokenGet(Resource):
    @np.doc(responses={200: '레시피 정보 불러오기 완료'})
    @np.doc(responses={404: '레시피 정보 불러오기 실패'})
    def post(self):
        return {
            'message': '레시피 정보 불러오기 완료',
            'status': 200,
            'data': recipes_temp
        }


if __name__ == '__main__':
    app.run(host='localhost', port=80)  # localhost:80 접속 시 Swagger