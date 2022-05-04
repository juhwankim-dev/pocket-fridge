"""
* flask_jwt_test
* flask, jwt 테스트
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.04
* 마지막 수정일 2022.05.04
"""
import jwt
from flask import Flask, request
from flask_restx import Resource, Api

app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask, JWT Test API',
    description='Flask, JWT 테스트를 위한 API'
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
    app.run(host='localhost', port=80, debug=True)  # localhost:80 접속 시 Swagger
