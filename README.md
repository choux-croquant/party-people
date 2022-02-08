<h1 align="center">🎉🎉 빠뤼 피플 🎉🎉</h1>

> 딱딱한 회의는 끝, 파티에 최적화된 빠뤼피플
>
> 빠뤼 피플은 기존의 딱딱한 화상 회의가 아닌, 참여자 다 같이 즐기는 컨텐츠가 준비된 비대면 파티 플랫폼입니다.

메인 페이지 썸네일 해결 후 이미지 추가

## 팀원 구성

### Front-End

* [최현규](https://lab.ssafy.com/choifrance) - 프론트엔드 리더
  * 메인 페이지 컴포넌트 개발
* [서민기](https://lab.ssafy.com/zcvn12345) - 프론트엔드 개발
  * 컨텐츠 컴포넌트 개발
* [이정환](https://lab.ssafy.com/ready2start) - 프론트엔드 개발
  * WebRTC 연결

### Back-End

* [하윤주](https://lab.ssafy.com/hayj04) - 백엔드 리더
  * 파티룸 수정/퇴장 API 개발
* [박건우](https://lab.ssafy.com/hggygw77) - 서버 환경 구축/관리
  * 파티룸 조회/검색 API 개발
* [선민기](https://lab.ssafy.com/suns1502) - QA
  * 파티룸 입장/삭제 API 개발

## 개발 환경

### Front-End

* Node.js 16.13.2
* Vue.js 3.2.29
* Vuex 4.0.2
* ESLint 6.7.2
* Axios 0.25.0
* Tailwind CSS 2.2.17
* PostCSS 7
* openVidu-browser 2.20.0

### Back-End

* OpenJDK 11.0.14.9-1
* spring boot 2.4.5
* Lombok 1.18.20
* QueryDSL 4.4.0
* Java-JWT 3.10.3
* Kurento-Client 6.16.0
* openVidu-Java-Client 2.20.0

### Server

* Docker 20.10.12
* Docker-compose 1.29.2
* MariaDB 10.2
* NGINX 1.21.6
* Kurento-media-server 6.16.0
* openVidu-Server  2.20.0

## 기획/설계 문서

### 와이어 프레임

* 랜딩 페이지

![Landing-Page](./result/Wireframe/Landing-Page.png)

* 메인 페이지

![Main-Page](./result/Wireframe/Main-Page.png)

* 파티룸 페이지

![Partyroom-Page](./result/Wireframe/Partyroom-Page.png)

* 파티룸 컴포넌트 배치 및 Modal

![Contents-Component](./result/Wireframe/Contents-Component.png)

* 파티룸 꾸미기 Modal

![Partyroom-Customizing-Modal](./result/Wireframe/Partyroom-Customizing-Modal.png)

* 화면 꾸미기 Modal

![Display-Customizing-Modal](./result/Wireframe/Display-Customizing-Modal.png)

### 프로토타입

#### 랜딩 페이지

![Landing-Page](./result/Prototype/Landing-Page.png)

#### 메인 페이지

* 메인 페이지

![Main-Page](./result/Prototype/Main-Page/Main-Page.png)

* 로그인 Modal

![Login-Modal](./result/Prototype/Main-Page/Login-Modal.png)

* 회원가입 Modal

![Signin-Modal](./result/Prototype/Main-Page/Signin-Modal.png)

* 방 생성 Modal

![Create-Partyroom-Modal](./result/Prototype/Main-Page/Create-Partyroom-Modal.png)

* 방 입장 Modal

![Enter-Password-Modal](./result/Prototype/Main-Page/Enter-Password-Modal.png)

#### 파티룸 페이지

* 파티룸 메인 페이지

![Partyroom-Page](./result/Prototype/Partyroom-Page/Partyroom-Page.png)

* 파티룸 꾸미기 Modal

![Partyroom-Customizing-Modal](./result/Prototype/Partyroom-Page/Partyroom-Customizing-Modal.png)

* 비디오 꾸미기 Modal

![Video-Customizing-Modal](./result/Prototype/Partyroom-Page/Video-Customizing-Modal.png)

* 룰렛 기능

![Roulette](./result/Prototype/Partyroom-Page/Roulette.png)

* 화이트보드 기능

![WhiteBoard](./result/Prototype/Partyroom-Page/WhiteBoard.png)

* 타이머 Modal

![Timer-Modal](./result/Prototype/Partyroom-Page/Timer-Modal.png)

* 투표 생성 Modal

![Vote-Create-Modal](./result/Prototype/Partyroom-Page/Vote-Create-Modal.png)

* 투표 Modal

![Vote-Modal](./result/Prototype/Partyroom-Page/Vote-Modal.png)

### 시퀀스 다이어그램

* 파티룸 리스트 조회

![List](./result/Sequence-Diagram/List.png)

* 파티룸 리스트 검색

![Search](./result/Sequence-Diagram/Search.png)

* 방 생성

![Create](./result/Sequence-Diagram/Create.png)

* 방 퇴장

![Exit](./result/Sequence-Diagram/Exit.png)

* 채팅

![Chat](./result/Sequence-Diagram/Chat.png)

* 룰렛

![Roulette](./result/Sequence-Diagram/Roulette.png)

* 타이머

![Timer](./result/Sequence-Diagram/Timer.png)

### ERD

![ERD](./result/ERD.png)

### 시스템 구성도

![Architecture](./result/Architecture.png)