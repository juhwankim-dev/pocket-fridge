# AWS EC2 환경 설정



## EC2에 MariaDB 설치

### 1. Update 실행

```bash
sudo apt-get update
```

![image_1](../exec/images/image_1.png)

### 2. MariaDB 설치

```bash
# -y 옵션은 이후 설치하겠습니까? 질문에 대답을 yes로 하겠다는 의미
sudo apt-get install mariadb-server -y
```

![image_2](../exec/images/image_2.png)

### 3.  MariaDB 실행

```bash
# MariaDB 실행
sudo systemctl start mariadb

# MariaDB 상태 확인
sudo systemctl status mariadb

# MariaDB 종료
sudo systemctl stop mariadb
```

![image_3](../exec/images/image_3.png)

![image_4](../exec/images/image_4.png)

![image_5](../exec/images/image_5.png)

### 4. root 비밀번호 설정

```bash
# root로 DB 접속
sudo mysql -u root -p

# MariaDB 설치 후 root 비밀번호를 설정한 적이 없으므로 비밀번호를 물어보는 질문에는 그냥 Enter
```

![image_6](../exec/images/image_6.png)

```bash
# 데이터베이스 목록 확인
show databases;

# mysql 데이터베이스 사용
use mysql;
```

![image_7](../exec/images/image_7.png)

![image_8](../exec/images/image_8.png)

```bash
# 비밀번호 정보 확인
select host, user, password from user;
```

![image_9](../exec/images/image_9.png)

```bash
# root의 비밀번호를 Ssafy6!으로 업데이트
update user set password=password('Ssafy6!') where user='root';

# 변경사항 적용
flush privileges;
```

![image_10](../exec/images/image_10.png)

### 5.  Pocket Fridge 데이터베이스 생성

```bash
# 데이터베이스 생성
create database pocket_fridge;
```

![image_11](../exec/images/image_11.png)

### 6. MariaDB 외부 접속 설정

```bash
# 외부 접속 설정을 위해 DB 설정 파일을 수정
sudo vim /etc/mysql/mariadb.conf.d/50-server.cnf

# bind-address 를 주석 처리
```

![image_12](../exec/images/image_12.png)

![image_13](../exec/images/image_13.png)

```bash
# MariaDB 재시작
sudo systemctl restart mariadb
```

![image_14](../exec/images/image_14.png)

```bash
# MariaDB 접속
sudo mysql -u root -p

# mysql 데이터베이스 사용
use mysql;

# 외부 접속을 허용할 유저 생성
create user 'andback'@'%' identified by 'Ssafy6!';

# 외부 접속할 유저에 모든 권한 부여
grant all privileges on *.* to 'andback'@'%' identified by 'Ssafy6!';

# 설정 적용
flush privileges;
```

![image_15](../exec/images/image_15.png)

![image_16](../exec/images/image_16.png)

### 7. 외부 접속 확인

![image_17](../exec/images/image_17.png)



## EC2에 Docker 설치

### 1. Update 실행

```bash
sudo apt-get update
```

![image_18](../exec/images/image_18.png)

### 2.  Docker 설치

```bash
# 도커 설치
sudo apt install docker.io -y

# 도커 버전 확인
docker -v

# 도커 실행
sudo service docker start

# Docker 그룹에 sudo 추가(도커 바로 제어할 수 있도록)
sudo usermod -aG docker ubuntu

# 권환 적용 확인을 위해 프로세스 확인 명령어로 체크
docker ps
```

![image_19](../exec/images/image_19.png)

![image_20](../exec/images/image_20.png)

![image_21](../exec/images/image_21.png)

- 위와 같이 Permission denied가 발생한다면 Docker 데몬 소켓파일(docker.sock)의 권한 변경이 필요

  ```bash
  # docker.sock 권한 변경
  sudo chmod 666 /var/run/docker.sock
  
  # 다시 한번 체크
  docker ps
  ```

  ![image_22](../exec/images/image_22.png)



## Docker에 Jenkins 설치

### 1. Jenkins 이미지 다운로드

```bash
# docker에 Jenkins 최신버전 이미지 다운로드
docker pull jenkins/jenkins:lts
```

![image_23](../exec/images/image_23.png)

### 2. Jenkins 컨테이너 실행

```bash
# jenkins-andback 이라는 이름으로 Jenkins 컨테이너 실행
docker run -itd --name jenkins-andback -p 8081:8080 jenkins/jenkins:lts
```

![image_24](../exec/images/image_24.png)

### 3. 초기 비밀번호 확인

```bash
# 위의 단계까지 완료후 k6d206.p.ssafy.io:8081 로 접속 시 Jenkins 비밀번호 입력 화면이 출력될 것이다.

# Jenkins 초기 비밀번호 확인
docker exec jenkins-andback cat /var/jenkins_home/secrets/initialAdminPassword

# 나온 비밀번호를 입력해주면 성공!
```

![image_25](../exec/images/image_25.png)

![image_26](../exec/images/image_26.png)

### 4. Jenkins 초기 설정

![image_27](../exec/images/image_27.png)

- Install suggested plugins 선택

![image_28](../exec/images/image_28.png)

- 정보 입력 후 Next

![image_29](../exec/images/image_29.png)

- Jenkins 도메인 주소 확인 후 Next

![image_30](../exec/images/image_30.png)

- 이 화면으로 넘어가면 초기 설정 완료!



## Jenkins 프로젝트 설정

### 1. Item 생성

![image_31](../exec/images/image_31.png)

![image_32](../exec/images/image_32.png)

- Item 이름 입력
- Freestyle 선택 후 OK

### 2. SSH 연결 설정

1. SSH Plugin 설치(Jekins 관리 → 플러그인 관리 → 설치 가능)

   ![image_33](../exec/images/image_33.png)

2. Key 파일 지정(Jenkins 관리 → 시스템 설정 → Publish over SSH 설정)

   - Key 입력 칸에 EC2 Pem 파일, 즉 K6D206T.Pem 파일 내용을 복사 / 붙여넣기 해준다.

     ![image_34](../exec/images/image_34.png)

3. SSH Server 추가 버튼 클릭 후 설정

   ![image_35](../exec/images/image_35.png)

   - Name : 원하는 이름으로 설정
   - Hostname : 도메인([k6d206.p.ssafy.io](http://k6d206.p.ssafy.io))
   - Username : ubuntu
   - Test Configuration 선택 후 Success 확인!!

### 3. Gitlab 연결 설정

1. Gitlab 플러그인 설치(Jenkins 관리 → 플러그인 관리 → 설치 가능)

   ![image_36](../exec/images/image_36.png)

2. Gitlab 설정(Jenkins 관리 → 시스템 설정 → Gitlab 설정)

   ![image_37](../exec/images/image_37.png)

   - Connection name : 원하는 이름으로 설정

   - Gitlab host URL : https://lab.ssafy.com

   - Credentials 설정(Add 버튼 클릭)

     1. Gitlab → User Settings → Access Tokens

     ![image_38](../exec/images/image_38.png)

     - Name : 원하는 이름으로 설정
     - Expires at : 필요한 기간만큼 설정
     - Scopes : 목적에 따라 체크

     1. Credentials 설정

        ![image_39](../exec/images/image_39.png)

        - API Token : 방금 발급받은 API Token 값
        - ID : 원하는 이름으로 설정
        - Description : 적지 않아도 무방

   1. 연결 확인

   ![image_40](../exec/images/image_40.png)

   - Test Connection 클릭 후 Success 확인!!

   ### 4. 프로젝트 설정

   - 만든 프로젝트 → 구성 → 소스 코드 관리

     ![image_41](../exec/images/image_41.png)

     - Repository URL : Gitlab Repository URL 주소

     - Credentials

       ![image_42](../exec/images/image_42.png)

       - Username : Gitlab 아이디
       - Password : Gitlab 패스워드
       - ID : 원하는 이름으로 설정
       - Description : 적지 않아도 무방

     - Branch는 develop으로 설정

   - 빌드 유발

     ![image_43](../exec/images/image_43.png)

     - Build when a change ... 체크

     ![image_44](../exec/images/image_44.png)

     - 빌드 유발 하단의 고급 버튼 클릭

     ![image_45](../exec/images/image_45.png)

     - Gitlab Webhook 설정을 위해 Generate 버튼으로 Secret token 발급

     ### GitLab Webhook 설정

     - Gitlab → setting → Webhooks

       ![image_46](../exec/images/image_46.png)

       - URL : Jenkins Item URL 주소
       - Secret Token : Jenkins에서 발급한 Token

     - Add Webhook 클릭

   - Build

     ![image_47](../exec/images/image_47.png)

     - Add build step → Execute shell 선택
     - 백엔드 프로젝트 빌드를 위해 아래의 명령어 입력
       - cd Backend/andback
       - chmod +x gradlew
       - ./gradlew clean build
     - Flask는 빌드 후 조치에서 작성

   - 빌드 후 조치

     ![image_48](../exec/images/image_48.png)

     ![image_49](../exec/images/image_49.png)

     - Name : 미리 설정해 둔 SSH
     - Source files : 배포할 파일 설정
     - Remove prefix : 제거할 접두사 설정
     - Remote directory : 배포할 파일이 저장될 디렉토리 설정, 이 때 EC2에 디렉토리가 없으면 에러 발생
     - Exec command : 배포가 된 후 실행할 명령어 작성

   - Shell Script

     - 아래의 쉘 스크립트를 EC2에서 작성해 ~/deploy/scripts 에 넣어준다.

     - init_spring_server.sh

       ![image_50](../exec/images/image_50.png)

       ```shell
       echo "> 현재 구동중인 Pocket Fridge Spring pid 확인"
        CURRENT_PID=$(ps -ef | grep java | grep andback | awk '{print $2}')
       echo "$CURRENT_PID"
       if [ -z $CURRENT_PID ]; then
       echo "> 현재 구동중인 Pocket Fridge Spring이 없으므로 종료하지 않습니다."
       else
       echo "> kill -9 $CURRENT_PID"
       kill -9 $CURRENT_PID
       fi
       sleep 5
       echo "> Pocket Fridge Spring이 실행됩니다..."
       nohup java -jar /home/ubuntu/deploy/Backend/andback/build/libs/andback-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy/logs/andbacklogs.log 2>&1 &
       ```

     - init_flask_server.sh

       ![image_51](../exec/images/image_51.png)

       ```shell
       echo "> 현재 구동중인 Pocket Fridge Flask pid 확인"
        CURRENT_PID=$(ps -ef | grep app | awk '{print $2}')
       echo "$CURRENT_PID"
       if [ -z $CURRENT_PID ]; then
       echo "> 현재 구동중인 Pocket Fridge Flask가 없으므로 종료하지 않습니다."
       else
       echo "> kill -9 $CURRENT_PID"
       kill -9 $CURRENT_PID
       fi
       sleep 5
       echo "> Pocket Fridge Flask가 실행됩니다..."
       pip install --upgrade pip
       nohup python3 /home/ubuntu/deploy/ML/main/app.py >> /home/ubuntu/deploy/logs/flasklogs.log 2>&1 &
       ```



## 빌드 시 사용되는 환경 변수

**Spring 빌드**

```bash
// Spring 빌드
./gradlew clean build

// 빌드 완료된 jar 파일을 백그라운드로 실행
nohup java -jar /home/ubuntu/deploy/Backend/andback/build/libs/andback-0.0.1-SNAPSHOT.jar &
```

**Flask 빌드**

```bash
// Flask 빌드 & 백그라운드로 실행
nohup python3 app.py &
```



**배포 시 특이사항**

- Spring은 8080번 포트 사용
- Flask는 5000번 포트 사용



**데이터베이스 접속 정보**

[File Path]

- S06P31D206 -> Backend -> andback -> main -> resources -> application-local.yml

![image_52](../exec/images/image_52.png)



## 사용하는 외부 서비스 정보

- Amazon EC2

  [SSH로 서버 접속]

  ```bash
  ssh -i K6D206T.pem ubuntu@k6d206.p.ssafy.io
  ```

  
