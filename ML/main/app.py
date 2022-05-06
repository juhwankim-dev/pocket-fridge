"""
* app.py
* docker에서 실행하기 위한 파일
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.06
* 마지막 수정일 2022.05.06
"""
from flask import Flask, request, json
from flask_restx import Resource, Api
import pymysql
import pandas as pd

# DB 연동
conn = pymysql.connect(
    host='k6d206.p.ssafy.io',
    port=3306,
    user='andback',
    password='Ssafy6!',
    db='pocket_fridge',
    charset='utf8',
    # cursorclass=pymysql.cursors.DictCursor
)

sql = "SELECT * from recipe"  # sql 쿼리문

recipes_temp = []
recipes_pd = pd.DataFrame

# 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
with conn:
    with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
        cur.execute(sql)  # 커서로 sql 실행
        # ['recipe_id', 'recipe_all_ingredient', 'recipe_content', 'recipe_food_name', 'recipe_image', 'recipe_serving', 'recipe_time', 'recipe_type', 'recipe_food_summary']
        columns_name = [i[0] for i in cur.description]  # 컬럼명 리스트
        records = cur.fetchall()  # 모든 레코드 가져오기
        for record in records:  # 각 레코드 가져와서 출력
            dictionary = dict(zip(columns_name, record))  # key: 컬럼명, value: 데이터값
            recipes_temp.append(dictionary)
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
    app.run(host='0.0.0.0', port=5000)  # localhost:5000 접속 시 Swagger
