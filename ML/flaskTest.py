"""
* flaskTest
* python과 Flask, Swagger 연동 테스트
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.03
* 마지막 수정일 2022.05.03
"""

from flask import Flask
from flask_restx import Resource, Api, reqparse

app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask Test API',
    description='Flask 테스트를 위한 API'
)  # Swagger에서 보기 위함 : 앱 초기화, 앱 버전, 제목, 설명

# 간단한 flask test
np = api.namespace('test', description='Flask 기본 테스트')  # flask_restx의 api.namespace로 api 생성


@np.route('/')
class Test(Resource):
    def get(self):  # self : 인스턴스 자신
        return 'Hello World!'


# get, put, delete 테스트
# @api.route("/")
# @api.response(200, 'Found')
# @api.response(404, 'Not found')
# @api.response(500, 'Internal Error')
# @api.param('text', 'String value')
rest = api.namespace('rest', description='REST API 테스트')


@rest.route("/<string:text>")
@rest.response(200, 'Found')
@rest.response(404, 'Not found')
@rest.response(500, 'Internal Error')
@rest.param('text', 'String value')  # Swagger 상 'text' 이름의 파라미터 생성
class Algorithm(Resource):
    # @api.doc('get')
    def get(self, text):
        return {'text': text}  # dictionary 형태의 response 값
        # return 'test'

    # @api.doc('delete')
    def delete(self, text):
        return text

    # @api.doc('put')
    def put(self, text):
        return text


if __name__ == '__main__':
    app.run(host='localhost', port=8080, debug=True)  # localhost:8080 접속 시 Swagger
