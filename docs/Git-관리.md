# Git 관리

</br>



> ### **Git Hook 기능을 사용해서 이슈를 바로 확인할 수  있도록 했습니다.**



![img](C:\Git\S06P31D206\docs\image\githook)

- `S06P31D206/.git/hooks` 위치로 이동
- 다운로드 파일 붙여넣기
- 이름 `prepare-commit-msg` 로 수정하기
- 적용 완료

![image-20220519111006186](C:\Git\S06P31D206\docs\image\githook2)

</br>

</br>



> ### **Git commit Template를 사용해서 컨벤션을 지켰습니다.**



#### 커밋 메시지 템플릿 설정

- 커밋 시 Git은 `commit.template` 옵션에 설정한 템플릿 파일을 보여준다.
- 커밋 메시지 템플릿을 지정하면 커밋 메시지를 작성할 때 **일정한 스타일**을 유지할 수 있다.

#### 템플릿 파일 생성하기

프로젝트 폴더안에 `.git-template.txt` 파일을 생성한다

아래 내용을 복사 붙어넣는다.

```
################
# <타입> : <제목> 의 형식으로 제목을 아래 공백줄에 작성
# 제목은 50자 이내 / 변경사항이 "무엇"인지 명확히 작성 / 끝에 마침표 금지
# 예) S06P21S004-121 [mo] feat: MainActivity 추가

# 바로 아래 공백은 지우지 마세요 (제목과 본문의 분리를 위함)

################
# 본문(구체적인 내용)을 아랫줄에 작성
# 여러 줄의 메시지를 작성할 땐 "-"로 구분 (한 줄은 72자 이내)

################
# feat : :sparkles: 새로운 기능 추가
# fix : :bug: 버그 수정
# docs : :memo: 문서 수정
# test : :white_check_mark: 테스트 코드 추가
# refact : :zap: 코드 리팩토링
# style : :art: 코드 의미에 영향을 주지 않는 변경사항
# chore : :apple: 빌드 부분 혹은 패키지 매니저 수정사항
# error : :rotating_light: 에러가 해결되지 않은 코드. merge request 하면 안 됨
################
```

![img](C:\Git\S06P31D206\docs\image\gitcommittemplate1)

#### 템플릿 설정하기

- 템플릿 파일을 설정해놓으면, `git commit` 명령을 실행할 때 지정한 템플릿 메시지를 편집기에서 매번 사용할 수 있다.

- 템플릿 파일을 설정한다는 것은 `commit.template`에 `.git-template.txt` 파일을 등록한다는 의미다.

- 템플릿 파일을 설정하는 명령은 아래와 같다.

  > git config --global commit.template `.git-template.txt`

![image-20220519111315419](C:\Git\S06P31D206\docs\image\gitcommittemplate2)

- `git add`

  > git add .

- `git commit`

  > git commit

![image-20220519111346126](C:\Git\S06P31D206\docs\image\gitcommittemplate3)

commit 메세지가 정상적으로 올라갔는지 보기

- `git log`

  > git log

정상적으로 올라갔다면 push

- `git push`

  > git push



![image-20220519111746289](C:\Git\S06P31D206\docs\image\gitdevelop)

>  이모지를 이용해서 어떤 점이 변경되었는지 쉽게 볼 수 있도록 했습니다.

![image-20220519111716797](C:\Git\S06P31D206\docs\image\gitmerge)

> label을 이용해서 무엇에 대한 변경인지 쉽게 볼 수 있도록 했습니다.

![image-20220519111957621](C:\Git\S06P31D206\docs\image\gitgraph)

</br>



## Merge Request

> Merge 하기 전, Merge Request가 오면 댓글과 좋아요를 통해 서로 격려하면서 즐겁게 개발했습니다. 
> 또한, 코드 리뷰도 진행했습니다.

![image-20220519111629850](C:\Git\S06P31D206\docs\image\gitcodereview)