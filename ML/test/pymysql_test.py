"""
* pymysql_test
* python과 mariaDB 연동 테스트
*
* @author 김다은
* @version 1.0.0
* 생성일 2022.05.03
* 마지막 수정일 2022.05.03
"""
import pymysql.cursors  # pymysql: python + MySQL 라이브러리

# MariaDB 서버에 접속하기
conn = pymysql.connect(
    host='k6d206.p.ssafy.io',
    port=3306,
    user='andback',
    password='Ssafy6!',
    db='pocket_fridge',
    charset='utf8',
    cursorclass=pymysql.cursors.DictCursor  # DB를 조회한 결과를 Column 명이 Key 인 Dictionary로 저장
)  # 접속 정보

sql = "SELECT * from user"  # sql 쿼리문

# 커서로 sql 쿼리문 출력 (with를 사용하기 때문에 conn.commit()과 conn.close() 불필요)
with conn:
    with conn.cursor() as cur:  # 커서생성 (cursor로 SQL 실행하여 데이터 가져올 수 있음)
        cur.execute(sql)  # 커서로 sql 실행
        result = cur.fetchall()  # 모든 레코드 가져오기
        for data in result:  # 각 레코드 가져와서 출력
            print(data)

# conn.commit()  # 저장
# conn.close()  # 종료
