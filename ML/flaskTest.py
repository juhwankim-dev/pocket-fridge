from flask import Flask
from flask_restx import Resource, Api, reqparse

app = Flask(__name__)  # 앱 초기화
api = Api(
    app,
    version='1.0',
    title='Flask Test API',
    description='Flask 테스트를 위한 API'
)  # 앱 초기화, 앱 버전, 제목, 설명


# 일반적인 라우트 방식
@api.route("/")
@api.response(200, 'Found')
@api.response(404, 'Not found')
@api.response(500, 'Internal Error')
@api.param('text', 'String value')
class Algorithm(Resource):
    @api.doc('get')
    def get(self, text):
        return {"text": "hello,world"}

    @api.doc('delete')
    def delete(self, id):
        return '', 204

    @api.doc('put')
    def put(self, id):
        return ""


if __name__ == '__main__':
    app.run(host='localhost', port=8080, debug=True)  # localhost:8080 접속 시 Swagger
