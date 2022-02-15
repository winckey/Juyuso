# 주 유 소 ? 🍻

![Github](https://img.shields.io/badge/vue-2.6.14-%234FC08D?style=plastic&logo=Vue.js)![Github](https://img.shields.io/badge/spring_boot-2.5.8-%236DB33F?style=plastic&logo=Spring)![Github](https://img.shields.io/badge/MySQL-8.0-%234479A1?style=plastic&logo=mysql)![Github](https://img.shields.io/badge/build-passing-brightgreen?style=plastic)



## :triangular_flag_on_post: 주 유 소 ? 🍻


- ### 프로젝트 개요

  - 🏠 [주유소 (酒流所)](https://i6e106.p.ssafy.io/)

  - `주 유 소`는 친구들, 혹은 새로운 사람들과 비대면으로 장소와 거리에 제약없이 재밌게 소통하며 술마시고 싶을 때 사용할 수 있는 서비스입니다.

- ### 주요 기능 

  - **화상기능 서비스**

    > 1) 자유롭게 방을 생성하고 친구 또는 새로운 사람들과 만남을 가질수 있다
    >
    > 2) 다른사람들과 **화상기능**을 이용해 자유로운 만남을 가질수 있다. 

  - **게임 및 재미 서비스** 

    > 1) 단순한 화상회의 서비스가 아닌 **여러가지 게임**을 통해 술자리에 맞는 분위기를 유도할수 있다. 
    >
    > 2) 사용자는 **카메라 필터 음성변조기능**을 이용하여 자신의 모습 , 목소리를 바꿀수 있다.
    >
    > 3) 각 방은 여러 배경을 변경하여 다양한 분위기에서 모임을 가질 수 있다.
    >
    > - 게임과 재미기능들을 이용해 즐거운 술자리를 유도한다.

  - **커뮤니티 서비스**

    > 1) 새로운 사람 혹은 친구와의 연락을 위해 친구 기능을 제공한다.
    >
    > 2) **일대일 채팅**을 통해 다양한 사람과 대화하고 만남을 주도한다.
    >
    > 3) **확성기 채팅**을 통해 여러 사람들에게 자신의 방을 홍보 하거나 만남을 주도 할 수 있다. 
    >
    > 4) **알림 기능**으로 친구기능 채팅이 왔을때 실시간으로 확인할 수 있다.
   - **마이데이터 서비스**

    > 1) 자신의 프로필 사진과 별명등을 변경한다.
    >
    > 2) **마이데이터**를 통해 출석정보 , 음주기록을 확인하고 저장할 수 있다.
    >
    > - 서로간의 정보를 공유하고 비교하며 재미를 느낄 수 있다.
    - **로그인 서비스**

    > 1) 로그인 기능을 이용하여 친구기능 마이데이터 서비스를 이용한다.
    >
    > 2) 웹 로그인과 카카오톡 로그인을 지원한다.

- ### 향후 계획

 



## 📌 목차

[주 유 소 ? 🍻](#triangular_flag_on_post-run-with-me--%EF%B8%8F) 

* [시작하기](#triangular_flag_on_post-시작하기)

  * [시작하기에 앞서](#시작하기에-앞서)
  * [설치하기](#설치하기)
  * [실행하기](#실행하기)
  * [배포하기](#배포하기)

* [지원하는 브라우저](#globe_with_meridians-지원하는-브라우저)

* [사용된 도구](#hammer_and_wrench-사용된-도구)

* [사용된 기술](#desktop_computer-사용된-기술)

* [시스템 아키텍쳐](#desktop_computer-시스템-아키텍쳐)

* [서비스 소개](#-서비스-소개)

* [일정](#calendar-일정)

* [저자](#-저자)

* [라이센스](#page_with_curl-라이센스)

  

## :triangular_flag_on_post: 시작하기

아래 방법을 따르시면 프로젝트를 실행시킬 수 있습니다.

### 시작하기에 앞서

* [Windows 10](https://www.microsoft.com/en-us/software-download/windows10)
* [JDK 1.8](https://www.oracle.com/kr/java/technologies/javase/javase-jdk8-downloads.html)
* [MySQL 8.0](https://www.mysql.com/downloads/)

### 설치하기

1. 깃헙의 레포지토리를 클론합니다.

   ```
   $ git clone https://lab.ssafy.com/s06-webmobile1-sub2/S06P12E101.git
   ```

2. npm을 설치합니다.

   ```
   $ npm install
   ```

### 실행하기

`주 유 소` 서비스를 사용하기 위해서는 다음과 같은 방법으로 실행합니다:

1. 데이터베이스를 설정합니다.

   - [RunWithMe_SQL.sql](https://github.com/soohyun0907/RunWithMe/tree/master/doc/ERD/RunWithMe_SQL.sql)을 참고해서 데이터베이스를 생성합니다. ([여기](https://github.com/soohyun0907/RunWithMe/tree/master/doc/ERD/RunWithMe_ERD.png)를 눌러 erd를 확인하세요.)

   - `application.yml`에 데이터베이스 설정을 추가합니다.

     ```
     spring:
       datasource:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url : jdbc:mysql://localhost:3306/juyuso?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
         username : {데이터베이스 계정 아이디}
         password : {데이터베이스 계정 비밀번호}
     ```

2. 백엔드 서버를 실행합니다.

   - IDE에 import 후 실행합니다.

     : IntelliJ, STS와 같은 IDE를 사용하는 경우, `BackEnd`폴더를 import하여 실행합니다.

   - jar파일을 생성 후 실행합니다.

     ```
     $ gradlew -DskipTests=true build
     ```

     ```
     $ java -jar [filename].jar
     ```

   - 프론트엔드를 실행합니다.

     ```
     $ npm run serve
     ```

### 배포하기

해당 서비스는 `AWS EC2`와 `docker`를 이용하여 배포하였습니다. 사전에 [여기]()를 참고해서 `AWS EC2`계정을 생성하세요.

배포를 하기위해서는 다음과 같은 방법으로 실행합니다:

1. AWS EC2 인스턴스 생성

2. Ubuntu 환경에 Git Clone

3. Nginx 환경 설정

   ```
   server {
   
           listen 80;
           listen [::]:80;
           server_name k3a303.p.ssafy.io;
           return 301 https://$host$request_uri;
           
   }
   
   server {
   
           listen 443 ssl default_server;
           listen [::]:443 ssl default_server;
          
           root /home/ubuntu/deploy/s03p31a303/frontend/RunWithMe/dist;
   
           # Add index.php to the list if you are using PHP
           index index.html index.htm index.nginx-debian.html;
   
           server_name k3a303.p.ssafy.io;
   
           location / {
                   # First attempt to serve request as file, then
                   # as directory, then fall back to displaying a 404.
                   try_files $uri $uri/ /index.html;
           }
   
           ssl_certificate /etc/letsencrypt/live/k3a303.p.ssafy.io/fullchain.pem;
           ssl_certificate_key /etc/letsencrypt/live/k3a303.p.ssafy.io/privkey.pem;
   
           access_log /var/log/nginx/proxy/access.log;
           error_log /var/log/nginx/proxy/error.log;
           
   }
   ```

4. JDK 설치 (환경변수 설정)

5. DB 설치 (해당 프로젝트에서 MySQL 사용)

6. gradle wrapper을 위한 버전 설정 (6.0.0 이상)

7. gradle clean build 실행 (war 파일 생성)

8. yarn build (dist 폴더 생성)




## :globe_with_meridians: 지원하는 브라우저

| Chrome | Safari | Edge   | Firefox |
| ------ | ------ | ------ | ------- |
| latest | latest | latest | latest  |

## :hammer_and_wrench: 사용된 도구

* Vue.js 2.6.11
* vue/cli 4.4.6
* yarn 1.22.4
* Spring boot 2.3.1
* Gradle 6.4.1
* IDE: Visual Studio Code 1.48, IntelliJ
* GitLab
* Jira
* Jenkins
* SonarQube


## :desktop_computer: 사용된 기술

![TechStack](https://user-images.githubusercontent.com/19357410/100544132-062d1380-3297-11eb-832e-9e1dd8f8da13.png)

**[ BACK END ]**

- **Spring Boot** : Run WIth Me Project의 전반적인 Rest Controller 구현.
- **Spring Security** : WebSecurityConfigurerAdapter를 상속받아 Filter를 적용, 사용자 권한에 맞는 기능을 수행하도록 구현. 
- **JWT** : JSON Web Token을 활용하여 회원 인증 및 안정성있는 정보 교환을 할 수 있도록 활용.
- **JPA (Hibernate)** : ORM인 Hibernate를 활용하여 객체 중심의 개발을 할 수 있도록 하였고, SQL을 직접 작성하지 않고 Entity 필드가 되는 객체를 통해 DB를 동작시켜 유지보수에 용이하게 활용. 
  - 동일한 쿼리에 대한 캐시 기능을 사용하기 때문에 높은 효율성 기대
- **SSL 프로토콜** : SSL을 적용하여 전송되는 패킷값을 암호화하여 외부의 공격자로부터 데이터를 보안하기 위해 사용.
  - **Let’s Encrypt** 무료 인증서를 발급받아 웹서버에 SSL 인증서를 적용.
  - Google Map API 활용 시, SSL 적용 필수.
- **MySql** : RDBMS로 Run With Me Project의 사용자, 러닝 기록, 챌린지, 게시판 등 필요한 데이터를 저장.
- **Redis** : 비관계형 데이터베이스로 'Key-Value' 구조 데이터 관리 시스템이며, 데이터를 메모리에 저장하여 빠른 처리속도가 필요한 기능에 적용.
  - 채팅 서버의 채팅방, 메세지 처리를 위해 사용. (Subscribe / Publish)
  - 만료일을 저장하면 만료 시 자동으로 데이터가 사라지는 특성을 활용하여 로그아웃된 토큰을 저장하여 블랙리스트로 활용.
- **AWS**
  - EC2 서비스를 이용하여 Ubuntu 서버를 구축 (호스팅).
  - S3 서비스를 이용하여 프로필 및 러닝 기록 (Polyline)을 저장하기 위해 사용.
- **Nginx** : 웹 서버를 구축
- **WebSocket** : 웹 상에서 쉽게 소켓 통신을 하게 해주는 라이브러리를 활용하여 러닝 메이트 매칭, 지역별 그룹 채팅 기능을 구현.
- **STOMP** : Http에 모델링된 frame 기반의 메세징 프로토콜을 통해 메세지 전송을 좀 더 효율적으로 하기 위해 활용.
  - `STOMP Handler`를 구현하여 Subscribe를 통해 통신하고자 하는 주체(Topic)를 판단하여 실시간, 지속적으로 감시하고 해당 요청이 들어오면 처리하도록 구현. **(Broker 역할 수행)**

**[ FRONT END ]**

- **Vue**
  * **Swal (Sweet Alert)** -  다양한 기능과 디자인을 가진 Alert창을 구현.
  * **SplideSlide** - Page를 Swap할때마다 변경되는 페이지.
  * **carousal** - Main Page에서 다양한 이벤트를 보기 편하게 하기 위해 3D Slide형식으로 넘어가는 컴포넌트 구현.
  * **apexChart** - 런닝 데이터 분석을 위해 사용하는 분석차트.
  * **decode-google-map-polyline** - String으로 인코딩했던 polyline을 맵에 다시 표시하기 위해 사용.
  * **vue-good-table** - 친구 목록이나 다양한 목록을 표현할때 사용되는 검색과 정렬기능, 페이지 기능을 사용.
  * **vue-moment** - 시간을 DB에 저장하기위해 포맷 형식을 변경 및 DB에서 가져올 때 출력 형식 변경.
  * **web-stomp** - 채팅기능을 위한 웹 소켓 프로그래밍, 실시간으로 채팅을 할때 사용.
- **Google Static Map** : 실시간 위치정보를 맵에 Polyline화해서 지도이미지를 DB에 저장.
- **Google Map** : 지도에 지속적으로 위치를 기록 및 추적하기 위해 사용.
- **Kakao Pay** : Kakao에서 제공하는 KakaoPay기능을 이용해 모바일로 결제가 가능. 기부 챌린지 참가를 위해 원하는 금액만큼 미리 충전하여 사용.
- **Vuex** : 여러 페이지에서 공용으로 사용하는 데이터와 함수들을 저장해서 한번에 관리, 추가적으로 JWT토큰을 스토어에 저장하여 Backend와 통신시마다 주고받으며, 2시간이 지나면 토큰이 만료되어 서비스 이용 불가. 
- **vue apaxcharts** : 러닝 데이터를 효과적으로 시각화할 수 있도록 활용.
  - axios 통신.
  - 시각화 자료 이미지 파일로 변환 및 저장.
  - 활용 사례
    - running plant
      - 월별 사용자 러닝 거리 기록.
      - 거리 기록에 따라 상대적인 색 표현.
      - 업데이트를 통한 실시간 기록 분석 가능.
     - running analysis
       - 지난 측정 기록 vuex 저장 및 시각화.
       - 업데이트 기록과 비교.
       - 사용자와 같은 티어의 사용자와 비교 분석.

**[ TEAM Cooperation ]**

- **GitLab** : GitLab을 활용하여 프로젝트를 관리.
  - Git Flow 에 따른 브랜치 전략 수립.
  - MR 시 코드 리뷰 진행.
- **Jira** : 이슈 관리 도구로 활용. 
  - 주요 기능들을 이슈로 등록하고 Story Point를 산정한 후, 담당자를 지정하여 프로젝트를 진행.
  - 1~2 주 정도 상황에 맞게 스프린트를 설정.
- **Google Drive** : 협업을 위한 공용 문서 및 산출물들을 공유할 수 있도록 활용.
  - 동시 문서 작성 (Google Docs).
  - 대용량 파일 첨부

## :desktop_computer: 시스템 아키텍쳐

![System_Architecture](https://user-images.githubusercontent.com/19357410/100544133-088f6d80-3297-11eb-8ba7-bad031aa6e46.png)

## 🎞 서비스 소개

### 1. 로그인 화면

#### 1-1. 로그인 화면



---

### 2. 테이블 화면

#### 2-1. 테이블 화면



- **[테이블 화면] :** 현재 생성되어 있는 방리스트를 확인 할수 있다.
- **[테이블 리스트 필터] :** 전체, 공개방, 비공개방을 설정에따라 원하는 것만 볼 수 있다.
- **[테이블 검색 기능] :** 방리스트를 태그별 제목별로 검색하여 원하는 것만 볼 수 있다.
---

#### 2-2. 테이블 화면 방 추가



- **[+버튼 클릭시] :** 새로운 방을 생성할 수 있도록 모달창을 띄운다.
- **[테마 선택 및 방정보 설정] :** 생성할 방의 기본 정보들을 정의 한다.

---

#### 2-3. 알림 기능


- **[알림 도착시] :** 알림을 통해 친구 신청 또는 메세지를 실시간으로 확인 할수 있다.

---

### 3. 사이드 바

#### 3-1. 사이드 바

<img src="https://user-images.githubusercontent.com/19357410/100543583-4e970200-3294-11eb-9b69-67f6f57f2af5.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543584-4f2f9880-3294-11eb-8812-6b609c48aa0e.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543586-4fc82f00-3294-11eb-8a76-a75ae0e5fba9.png" width="30%">

* **[개인정보] :** 자신의 간단한 개인정보를 볼수 있다.
* **[마이페이지 클릭시] :** 개인정보를 수정할수 있는 마이페이지로 이동한다.
* **[로그아웃 클릭시] :** 로그아웃 되며 로그인 페이지로 이동한다.
* **[사람모양 클릭시] :** 친구리스트를 볼수 있다.
* **[말풍선모양 클릭시] :** 현재 대화상대와의 대화창으로 이동.
* **[돋보기 모양 클릭시] :** 친구검색창으로 이동.

---

#### 3-2. 친구리스트



* **[친구리스트] :** 현재 친구와 친구 신청 리스트를 보여준다.
* **[친구를 클릭시] :** 친구의 프로필 정보 또는 대화신청등의 기능을 실행할 수 있다.
---

#### 3-3. 1대1 대화창


* **[대화창] :** 대화창에서 지금까지 상대와 대화한 목록을 볼 수 있고 상대방과의 실시간 채팅을 할수 있다.
---
#### 3-4. 검색창

* **[검색어 입력] :** 해당 닉네임을 가진 사용자들을 검색하며 친구가 아닐경우 친구 신청을 할 수 있다.
---

### 4. 마이페이지

#### 4-1. 마이페이지



* **[마이페이지] :** 자신의 프로필 정보를 확인 및 수정할 수 있다.
* **[마이데이터 클릭시] :** 자신의 음주 기록페이지로 이동한다.
* **[캘린더 클릭시] :** 자신의 출석정보페이지로 이동한다.
* **[차단친구 관리] :** 자신이 차단한 친구를 확인 할 수 있다.
---

#### 4-1. 마이페이지



* **[마이페이지] :** 자신의 프로필 정보를 확인할 수 있다.
* **[마이데이터 클릭시] :** 자신의 음주 기록페이지로 이동한다.
* **[캘린더 클릭시] :** 자신의 출석정보페이지로 이동한다.
* **[차단친구 관리] :** 자신이 차단한 친구를 확인 할 수 있다.
---

#### 4-2. 마이데이터



* **[마이데이터] :** 자신의 음주 기록과 간단한 코멘트를 확인할 수 있다.
* **[오늘 주량 추가] :** 어플리케이션 외에서 마신 주량을 기록할 수 있다.

---

#### 4-3. 캘린더



* **[출석] :** 출석체크 및 자신의 출석 현황을 파악할 수 있다.

---

### 5. 미팅 페이지

#### 5-1. 일대일 채팅

<img src="https://user-images.githubusercontent.com/19357410/100543962-1c869f80-3296-11eb-8d9b-342b48349992.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543963-1d1f3600-3296-11eb-9a7c-782bb19479fd.jpg" width="30%">

* **[일대일 채팅] :** 팔로우한 유저의 온라인 접속 상태가 보이며 온라인인 유저와 실시간으로 채팅한다.

---

#### 5-2. 매칭

<img src="https://user-images.githubusercontent.com/19357410/100543929-0d9fed00-3296-11eb-8ca0-1e0df7388ee6.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543930-0f69b080-3296-11eb-929b-1c1e3bc5fb1b.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543932-0f69b080-3296-11eb-83d0-83adcb3e1959.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543933-10024700-3296-11eb-934f-9b131c543022.jpg" width="30%">

* **[일대일 채팅 밑 매칭 클릭 시] :** 일대일 채팅의 온라인 및 오프라인 유저의 목록 하단 매칭 버튼 클릭 시 경험치에 따른 유저를 매칭해준다.
* **[매칭 시스템] :** 성별을 선택 후 원하는 러너를 선택해 팔로우한 다음 일대일 채팅을 시작한다.

---

### 6. 지역 채팅

#### 6-1. 지역 채팅

<img src="https://user-images.githubusercontent.com/19357410/100543934-11337400-3296-11eb-93df-4bb018129209.jpg" width="30%">

* **[지역 채팅] :** 원하는 지역을 선택 시 해당 지역에서 여러 유저와 실시간으로 채팅한다.

---

### 7. 챌린지 페이지

#### 7-1. 챌린지 페이지

<img src="https://user-images.githubusercontent.com/19357410/100543935-1264a100-3296-11eb-95db-bd848289a472.jpg" width="30%">

* **[챌린지 페이지] :** 상단의 보유 마일리지가 표시되고, 현재 진행중, 진행 예정, 종료된 챌린지를 확인한다.

---

#### 7-2. 챌린지 상세

<img src="https://user-images.githubusercontent.com/19357410/100543936-12fd3780-3296-11eb-9392-7269ed244161.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543937-12fd3780-3296-11eb-8ea9-7c279b4bfeff.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/33771279/100744077-964a9480-3420-11eb-995d-97c2903c193d.PNG" width="30%"> <img src="https://user-images.githubusercontent.com/19357410/100543938-1395ce00-3296-11eb-8479-a79389bf8ff3.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543939-142e6480-3296-11eb-9db2-4ed86ddb56f1.jpg" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543941-142e6480-3296-11eb-907b-b69543697523.jpg" width="30%">

* **[챌린지 클릭 시] :** 챌린지 클릭 시 해당 챌린지의 상세 정보를 확인하고, 신청 버튼을 통해 챌린지에 참여한다.
* **[챌린지 신청 버튼 클릭 시] :** 챌린지 신청 버튼 클릭 시 챌린지 참여 페이지로 넘어가며, 기부 금액을 설정해 참여한다. 이 때, 기부 금액은 0원을 설정해도 참여가 가능하며, 해당 금액은 미리 충전된 마일리지에서 차감된다.
* **[충전하기 버튼 클릭 시] :** 충전하기 버튼을 클릭 시, [마이페이지]-[마일리지 충전] 탭으로 전환되며 카카오페이를 통해 충전이 가능하다.

---

### 8. 챌린지 제안

#### 8.1 챌린지 제안

<img src="https://user-images.githubusercontent.com/33771279/100743407-98f8ba00-341f-11eb-9757-517c487ae721.PNG" width="30%">  <img src="https://user-images.githubusercontent.com/33771279/100743427-9e560480-341f-11eb-99bc-94b88b1b6654.PNG" width="30%"> <img src="https://user-images.githubusercontent.com/33771279/100743445-a2822200-341f-11eb-8396-e8c922af8ecc.PNG" width="30%"> <img src="https://user-images.githubusercontent.com/33771279/100743436-a0b85e80-341f-11eb-809b-63bbd62e7a9a.PNG" width="30%"> <img src="https://user-images.githubusercontent.com/33771279/100743434-9f873180-341f-11eb-8684-ec48e036a39f.PNG" width="30%"> <img src="https://user-images.githubusercontent.com/33771279/100743430-9e560480-341f-11eb-875b-d814ff91a8aa.PNG" width="30%">

* **[챌린지 제안] :** 유저가 관리자에게 챌린지를 제안한다.

---

#### 8.2 챌린지 관리 페이지

<img src="https://user-images.githubusercontent.com/19357410/100543945-15f82800-3296-11eb-89fe-b7faf669e5e0.JPG" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543947-1690be80-3296-11eb-9e8a-89af452253e0.JPG" width="30%"> <img src="https://user-images.githubusercontent.com/33771279/100744077-964a9480-3420-11eb-995d-97c2903c193d.PNG" width="30%">

* **[챌린지 관리 페이지] :** 챌린지 관리 페이지는 관리자 등급만 확인 가능하며, 챌린지 생성, 삭제, 수정이 가능하다.

---

### 9. 러닝 기록 조회

#### 9-1. 러닝 기록 조회

<img src="https://user-images.githubusercontent.com/19357410/100543948-17295500-3296-11eb-8d14-165996f4ae60.JPG" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543949-17295500-3296-11eb-8a84-4eb34aac749e.JPG" width="30%">

* **[러닝 기록 조회] :** 본인의 모든 러닝 기록을 조회한다. 하단에서 러닝 기록을 클릭 시, 지도에 러닝 경로가 표시된다.
* **[활동 지역 러닝 기록 조회] :** 본인이 설정한 활동 지역 러닝 기록을 조회한다. 하단에서 러닝 기록을 클릭 시, 지도에 러닝 경로가 표시된다.
* **[러닝 기록 클릭 시] :** 러닝 상세 페이지로 이동한다.

---

### 10. 팔로우 목록

#### 10-1. 팔로우 목록 조회

<img src="https://user-images.githubusercontent.com/19357410/100543950-17c1eb80-3296-11eb-99b0-9f415388e0dd.JPG" width="30%">

* **[팔로우 목록 조회] :** 팔로워의 간단한 정보와, 말풍선 아이콘을 클릭 시 일대일 채팅으로 이동하며, 엑스 아이콘을 클릭 시 팔로우를 취소한다.

---

### 11. 유저 정보 수정

#### 11-1. 유저 정보 수정

<img src="https://user-images.githubusercontent.com/19357410/100543951-185a8200-3296-11eb-9472-75905c604228.JPG" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543952-18f31880-3296-11eb-94de-1cb7ac9e91c6.JPG" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543954-198baf00-3296-11eb-8438-a602935e4f9e.JPG" width="30%">  <img src="https://user-images.githubusercontent.com/19357410/100543957-1a244580-3296-11eb-9d21-354d7693f06c.JPG" width="30%">

* **[유저 정보 수정] :** 유저 정보를 수정하거나 탈퇴한다.
* **[유저 프로필 수정] :** 유저 프로필을 수정한다.

---

## :calendar: 일정

![일정](https://user-images.githubusercontent.com/19357410/100542772-7d5ea980-328f-11eb-806c-4bd76138aa1e.png)

## 👤 저자

* 김대용 - Daeyong Kim - kim940813@naver.com - @[imdaeyong](https://github.com/imdaeyong) [Front]
* 김순빈 - Soonbeen Kim - ksb940925@gmail.com - @[soo-ni](https://github.com/soo-ni) [Back / PM]
* 김형택 - Hyungtaik Kim - kimhyungtaik@gmail.com - @[hyungtaik](https://github.com/hyungtaik) [Back]
* 이선수 - Sunsoo Lee - tjstn921030@gmail.com - @[LEESUNSOO](https://github.com/LEESUNSOO) [Back]
* 전수현 - Soohyun Jeon - soohyun0907@naver.com - @[soohyun0907](https://github.com/soohyun0907) [Front]

