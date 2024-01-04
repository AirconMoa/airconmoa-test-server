[![Deploy to EC2](https://github.com/AirconMoa/AirconMoa-Server/actions/workflows/CICD.yml/badge.svg)](https://github.com/AirconMoa/AirconMoa-Server/actions/workflows/CICD.yml)
# 🙌 어려운 시스템 에어컨 견적비교를 손쉽게! - Airconmoa
<br>
<div align="center">
<img src="https://github.com/AirconMoa/airconmoa-test-server/blob/main/logo.png" height="350">
</div>
<br>
<br>

**Airconmoa**는 시스템 에어컨 견적 비교 플랫폼으로, 그동안 시스템 에어컨 업체 간의 비교 및 견적, 후기 등을 알기 어려웠던 문제점을 해결하기 위해 제작된 서비스입니다. Aiconmoa 어플리케이션 개발에는 GDSC-Inha 소속 인하대학교 재학생들이 참여하였으며, Project Manager, Designer, FrontEnd, BackEnd로 구성된 총 7명의 팀원이 참여하였습니다.
<br>
<br>
## 👨‍🏫 프로젝트 소개
Airconmoa 어플리케이션의 주요 기능은 에어컨 설치/수리/청소 견적, 신뢰할 수 있는 업체 정보 제공, 고객의 이용후기 제공입니다.
> **에어컨 설치/수리/청소 견적**: 고객이 자신의 시스템 에어컨 설치/수리/청소에 대한 요구사항을 포함한 견적을 요청합니다.
> 
> **신뢰할 수 있는 업체 정보 제공**: 업체의 설치 이력, 인증 정보 등 다양한 업체 정보를 제공하여 고객이 합리적인 서택을 할 수 있도록 유도합니다.
> 
> **고객의 이용후기 제공**: 업체에 대한 신회성 확보와 보다 합리적인 견적을 위한 고객의 이용 후기를 공유할 수 있습니다.
<br>
<h2 id="teamInfo"> 👨‍👨‍👦‍👦 개발자 소개</h2>

<table width="950">
    <thead>
    </thead>
    <tr>
        <th>사진</th>
        <td width="100" align="center">
            <a href="https://github.com/minjgziii">
                <img src="https://avatars.githubusercontent.com/u/104371003?v=4" width="150" height="150">
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/jaewonLeeKOR">
                <img src="https://avatars.githubusercontent.com/u/58386334?v=4" width="150" height="150">
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/lxxyxin">
                <img src="https://avatars.githubusercontent.com/u/98477056?v=4" width="150" height="150">
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/gmlstjq123">
                <img src="https://github.com/AirconMoa/airconmoa-test-server/blob/main/%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C.jfif" width="150" height="150">
            </a>
        </td>
    </tr>
    <tr>
        <th>이름</th>
        <td width="100" align="center">김민지</td>
        <td width="100" align="center">이재원</td>
        <td width="100" align="center">심찬희</td>
        <td width="100" align="center">변현섭</td>
    </tr>
    <tr>
        <th>역할</th>
        <td width="150" align="center">
            견적 요청서 
        </td>
        <td width="150" align="center">
            CI/CD
        </td>
        <td width="150" align="center">
            유저, 업체
        </td>
        <td width="150" align="center">
            견적서, 서버<br>배포, 아키텍쳐
        </td>
    </tr>
    <tr>
        <th>GitHub</th>
        <td width="100" align="center">
            <a href="https://github.com/minjgziii">
                <img src="http://img.shields.io/badge/gmlstjq123-green?style=social&logo=github"/>
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/jaewonLeeKOR">
                <img src="http://img.shields.io/badge/rossssa-green?style=social&logo=github"/>
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/kick-sim">
                <img src="http://img.shields.io/badge/songhaechan-green?style=social&logo=github"/>
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/gmlstjq123">
                <img src="http://img.shields.io/badge/duddn2012-green?style=social&logo=github"/>
            </a>
        </td>
    </tr>
  
</table>
<br>

## ⏲️ 개발 기간 
- **프로젝트 기간**: 2023.09.15(금) ~ 2023.11.18(토)
- **전체 회의**: 매주 일요일 오후 8시
  <br>
  <br>
## 📝 프로젝트 아키텍쳐
<div align="center"><a href="https://github.com/AirconMoa/airconmoa-test-server/blob/main/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90.png">
<img src="https://github.com/AirconMoa/airconmoa-test-server/blob/main/airconmoa-architecture.png" height="500"></a>
</div>

## ✒️ 협업 규칙
 **1. 브랜치 전략**: Gihub Flow 전략 사용

 **2. Issue 네이밍 규칙**: 명확한 의도를 나타낼 수 있도록 한글로 작성

 **3. Pull Request 네이밍 규칙**:<br>
   > 새로운 기능 추가 → feature/기능명<br>
   > 기존 내용 수정 → refactor/기능명 or fix/issue-#number<br>
   > 기존 내용 삭제 → remove/기능명<br>
   > 기능 테스트 → test/기능명<br>
   > 문서 관련 작업 → document/기능명

 **4. Commit Message 네이밍 규칙**: 이슈 네임과 동일
<br>
<br>

## 💻 개발 환경
- **Version** : Java 17, Python 3.10.12
- **IDE** : IntelliJ, Visual Studio Code
- **Framework** : SpringBoot 2.7.11
- **ORM** : JPA
<br>

## ⚙️ 기술 스택
- **Server**: Java, Python, EC2
- **Load Balancing**: Auto Scaling, ELB
- **DNS**: Route 53, ACM
- **DataBase**: RDS, Datagrip, JPQL, ERD AqueryTool, S3, Redis
- **WS/WAS**: Nginx, Tomcat
- **CI/CD**: Github Actions, CodeDeploy, S3
- **협업 관리**: Notion, Google Meet, Discord, Github, Figma
- **기타**: Firebase, SQS, Lambda, CloudWatch
<br>

## 📱 기능 설명 

### ✍ 소셜 로그인 서비스
> ✔ 구글, 네이버, 카카오 계정을 이용한 간편 회원가입 서비스를 제공합니다.<br>
> ✔ 앱 자체 로그인 기능도 지원합니다.
<br>

### 🔑 자동 로그인 서비스
> ✔ 스플래시 화면에서 자동 로그인을 수행합니다.<br>
> ✔ 업체와 일반 사용자 모두에게 지원되는 서비스입니다.
<br>

### 📣 고객측 견적 요청 서비스
> ✔ 시스템 에어컨 설치 정보와 함께 견적요청서를 작성합니다.<br>
> ✔ 다양한 시스템 에어컨 업체에서 견적 요청에 대한 견적서를 작성합니다.
<br>

### 📝 업체측 견적서 작성 서비스
> ✔ 고객이 요청한 견적서 목록을 확인할 수 있습니다.<br>
> ✔ 업체에서 제시하는 금액과 날짜를 견적서로 작성하면 고객이 확인할 수 있습니다.
<br>

**UI/UX 디자인**: <https://www.figma.com/file/OGnHMKFdeuw5vRMR8oiOq9/MOA_%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84?type=design&node-id=0-1&mode=design&t=UGc0UhiQyZhEcyWj-0>



