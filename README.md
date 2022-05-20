# 포켓프리지

![포켓프리지](./docs/image/pocketfridge.png)

`냉장고 관리 & 레시피 추천 APP` 

**음식은 두면 썩는다 빨리 먹어 치워야 한다**

> 냉장고에 있는 음식이 썩어가고 있지 않으신가요? 무엇을 만들어 먹을지 찾고 계신가요?<br>
>
> 나의 냉장고 안에 남아있는 재료를 깨워서 음식을 만들어보세요.<br>
>
> ''**주머니**'' 속으로  ''**냉장고**''를 넣으러  **Go Go !**
>
> [포켓프리지 바로가기]() 

<br><br>

## 목차
- [포켓프리지](#향해)
  - [목차](#목차)
  - [서비스 소개](#서비스-소개)
    - [📋 기술 스택](#-기술-스택)
  - [프로젝트 파일 구조](#프로젝트-파일-구조)
  - [산출물](#산출물)
  - [결과물](#결과물)

- [📺 프로젝트 UCC](https://www.youtube.com/watch?v=1MjfYxVGJi8)
  <br><br>

## 서비스 소개

1. 개발 기간 : 2022.04.11 ~ 2022.05.20 ( 총 6주 )
   - Sub1 : 2022.04.11 ~ 2022.04.22 ( 기획 & 설계 )
   - Sub2 : 2022.04.25 ~ 2022.05.20 ( 구현 & 배포 & 테스트 )
2. 인원 (총 6인)
	 - 김영훈 : 팀장, Back-end,  Spring Boot
	 - 김다은 : Back-end, Machine Learning, Spring Boot
	 - 김도연 : Android 
	 - 김민수 : 부팀장, Android 
	 - 김주환 : Android, Design
	 - 문관필 : Infra, Back-end,  Spring Boot

<br><br>

### 📋 기술 스택

1. 이슈관리 : ![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
2. 형상관리 : ![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)
3. 커뮤니케이션 : <img src ="https://img.shields.io/badge/Mattermost-blue">![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
4. 개발 환경
	- OS : ![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)10
	- IDE
	  -  <img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" alt="IntelliJ IDEA" style="zoom:80%;" /> 2021.3.1
	  - UI/UX : ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
	- Database : <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white" alt="MariaDB" style="zoom:80%;" />
	- Server : ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
		-  ![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white) 
5. 상세 사용
	- Backend
		-  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" alt="Java" style="zoom:80%;" /> (Zulu Open JDK 11)
		-  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" style="zoom: 80%;" /> (Spring Boot 2.6.6)
		-  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)7.4
		-  <img src ="https://img.shields.io/badge/Lombok-pink"></img>, <img src ="https://img.shields.io/badge/Swagger-green"></img> 3.0.0, <img src ="https://img.shields.io/badge/JPA-pink"></img>, <img src ="https://img.shields.io/badge/QueryDsl-red"></img> 5.0.0
	- Android
		- ![Android](https://img.shields.io/badge/Android-%232C531.svg?style=for-the-badge&logo=android&logoColor=white) 
	- AWS EC2
	   -   ![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white) 
	   -   ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white) 
	- Big Data/ML
	  -   <img src="https://img.shields.io/badge/ScikitLearn-%23FF6F00.svg?style=for-the-badge&logo=TensorFlow&logoColor=white" alt="TensorFlow" style="zoom:80%;" /> 
	  -   ![Anaconda](https://img.shields.io/badge/Anaconda-%2344A833.svg?style=for-the-badge&logo=anaconda&logoColor=white) 
	  -   ![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54) 3.9.12
	  -   ![Flask](https://img.shields.io/badge/flask-FF0000?style=for-the-badge&logo=flask&logoColor=000000) 

<br><br>

## 프로젝트 파일 구조

- Backend

```
backend/main
├── java
│   └── com
│       └── ssafy
│           └── andback
│               ├── AndbackApplication.java
│               ├── aop
│               │   └── 1.txt
│               ├── api
│               │   ├── constant
│               │   ├── controller
│               │   ├── dto
│               │   │   ├── request
│               │   │   └── response
│               │   ├── exception
│               │   │   ├── handler
│               │   │   └── mattermost
│               │   └── service
│               ├── config
│               │   ├── auth
│               │   └── jwt
│               └── core
│                   ├── domain
│                   │   ├── auth
│                   │   └── mattermost
│                   ├── queryrepository
│                   └── repository
└── resources
```

- Android

```
Android/main
├── AndroidManifest.xml
├── ic_launcher-playstore.png
├── java
│   └── com
│       └── andback
│           └── pocketfridge
│               ├── data
│               │   ├── api
│               │   ├── db
│               │   │   └── dao
│               │   ├── di
│               │   ├── infra
│               │   ├── mapper
│               │   ├── model
│               │   └── repository
│               │       ├── Recipe
│               │       ├── RecipeRepositoryImpl.kt
│               │       ├── UserRepositoryImpl.kt
│               │       ├── barcode
│               │       ├── category
│               │       ├── fridge
│               │       ├── ingredient
│               │       ├── like
│               │       ├── notification
│               │       └── user
│               ├── domain
│               │   ├── infra
│               │   ├── model
│               │   ├── repository
│               │   └── usecase
│               │       ├── barcode
│               │       ├── category
│               │       ├── datastore
│               │       ├── fridge
│               │       ├── ingredient
│               │       ├── like
│               │       ├── notification
│               │       ├── recipe
│               │       └── user
│               └── present
│                   ├── config
│                   ├── service
│                   ├── utils
│                   ├── views
│                   │   ├── main
│                   │   │   ├── DatePickerFragment.kt
│                   │   │   ├── FridgeListAdapter.kt
│                   │   │   ├── MainActivity.kt
│                   │   │   ├── MainViewModel.kt
│                   │   │   ├── SelectRegiIngreBottomSheet.kt
│                   │   │   ├── SubCategoryRVAdapter.kt
│                   │   │   ├── barcode
│                   │   │   │   ├── BarcodeScanFragment.kt
│                   │   │   │   └── BarcodeScanViewModel.kt
│                   │   │   ├── fridge
│                   │   │   │   ├── EditCategorySelectFragment.kt
│                   │   │   │   ├── FridgeFragment.kt
│                   │   │   │   ├── FridgeListBottomSheet.kt
│                   │   │   │   ├── FridgeViewModel.kt
│                   │   │   │   ├── IngreDetailFragment.kt
│                   │   │   │   ├── IngreDetailViewModel.kt
│                   │   │   │   ├── IngreEditFragment.kt
│                   │   │   │   ├── IngreEditViewModel.kt
│                   │   │   │   └── IngreRVAdapter.kt
│                   │   │   ├── ingreupload
│                   │   │   │   ├── CategorySelectFragment.kt
│                   │   │   │   ├── IngreUploadFragment.kt
│                   │   │   │   └── IngreUploadViewModel.kt
│                   │   │   ├── mypage
│                   │   │   │   ├── MyPageFragment.kt
│                   │   │   │   ├── MyPageViewModel.kt
│                   │   │   │   ├── NotiSettingFragment.kt
│                   │   │   │   ├── NotiSettingViewModel.kt
│                   │   │   │   ├── fridgemanage
│                   │   │   │   ├── userdelete
│                   │   │   │   └── useredit
│                   │   │   ├── notification
│                   │   │   └── recipe
│                   │   └── user
│                   │       ├── UserActivity.kt
│                   │       ├── findpw
│                   │       ├── login
│                   │       └── signup
│                   └── workmanager
│                       └── DailyNotiWorker.kt
└── res
```

<br><br>

## 산출물
- [프로젝트 메모 및 공유 : Notion](https://hoonycode.notion.site/00ab4c974a4c425f85f2d583836f8a01)
- [프로젝트 회의록](https://www.notion.so/f4eae148d8054706806e4ef2961e853d?v=3ab42d956592499eac3af07526819c7a)

<br>

- [프로젝트 컨벤션 목록](https://hoonycode.notion.site/7fd00175c468471aa33419d3c987262b)
- [기획서](https://hoonycode.notion.site/a5480c306a3a4ca2abb2ed99f8cd76b5)
- [API Docs](https://hoonycode.notion.site/API-264dab99d8904b7a83fed32e2987d6b6)
- [와이어 프레임](https://hoonycode.notion.site/86a1e54af51041a1a113a18767d5f6af)
- [서비스 아키텍처](https://hoonycode.notion.site/10c465198efe4a50a7c5119e23c33ebb)
- [기능 명세서](https://hoonycode.notion.site/9df8027f27064663a01a1d87d550035c)
- [데이터베이스:ERD](./docs/ERD.md)
- [시퀀스 다이어그램](https://hoonycode.notion.site/c3038cdcbbbb4c9591d167b477002faa)
- [Git 협업](./docs/Git-관리.md)
- [Jira 이슈관리](./docs/Jira-이슈관리.md)
- [AWS EC2 환경 설정](./docs/AWS-EC2.md)

<br><br>

## 결과물
- [포팅메뉴얼](./exec/포팅메뉴얼.md)
- [시연시나리오](./exec/시연시나리오.md)
- [중간발표자료](./docs/중간발표자료.pdf)
- [최종발표자료](./docs/최종발표자료.pdf)
