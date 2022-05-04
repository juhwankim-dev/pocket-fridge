"""
* db_pandas_test
* mariaDB 유저 식재료 데이터, 레시피 데이터 가져오기
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.04
* 마지막 수정일 2022.05.04
"""
import jwt
from flask import Flask, request
from flask_restx import Resource, Api, fields
import pymysql

app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask Test API',
    description='Flask 테스트를 위한 API'
)  # Swagger에서 보기 위함 : 앱 초기화, 앱 버전, 제목, 설명

# jwt secret key 설정 (Spring jwt와 같은 secret key)
JWT_SECRET_KEY = 'SSAFYPocketFridge'
algorithm = 'HS256'

# flask_restx의 api.namespace로 api 생성
np = api.namespace('jwt', description='jwt 테스트')


@np.route('/get')
class TokenGet(Resource):
    @np.doc(responses={200: 'jwt 토큰 확인 완료'})
    @np.doc(responses={404: 'jwt 토큰 확인 실패'})
    def post(self):
        header = request.headers.get('Authorization')  # Authorization 헤더로 담음
        if header is None:
            return {'message': 'Please Login'}, 404
        data = jwt.decode(header, JWT_SECRET_KEY, algorithms=algorithm)
        return data, 200


if __name__ == '__main__':
    app.run(host='localhost', port=80, debug=True)  # localhost:8080 접속 시 Swagger

# conn = pymysql.connect(
#     host='k6d206.p.ssafy.io',
#     port=3306,
#     user='andback',
#     password='Ssafy6!',
#     db='pocket_fridge',
#     charset='utf8'
# )
#
# sql = "SELECT * from user"  # sql 쿼리문
#
# # 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
# with conn:
#     with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
#         cur.execute(sql)  # 커서로 sql 실행
#         result = cur.fetchall()  # 모든 레코드 가져오기
#         for data in result:  # 각 레코드 가져와서 출력
#             print(data)
