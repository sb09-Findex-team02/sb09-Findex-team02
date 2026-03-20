# 2팀
- 노션 링크: https://www.notion.so/329d451c30cf8322873201044e8010e3

---

##  팀원 구성
- 강현홍 (github.com/Newbress)
- 한성재 (github.com/seonghj)
- 박지은 (github.com/clover6559)
- 박성호 (github.com/parksh3070)
- 임혜민 (github.com/hyemin-L)
---

## 프로젝트 소개
- 외부 API 연동 금융 분석 도구 사이트의 Spring 백엔드 시스템 구축
- 프로젝트 기간: 2026.03.11 ~ 2026.03.20
- 배포 링크: sb09-findex-team02-production.up.railway.app
- 시연 영상: https://drive.google.com/file/d/1-70iuQdShcrgSPylxgFS_tp-11bxBZY8/view
- 스크린샷
<img width="1915" height="1076" alt="findex1" src="https://github.com/user-attachments/assets/7b3581e7-3fb1-4864-8f33-059e1fe77b9f" />
<img width="1903" height="1069" alt="findex2" src="https://github.com/user-attachments/assets/941db029-24e3-443f-89fd-1aef526b8e19" />
<img width="1917" height="1078" alt="findex3" src="https://github.com/user-attachments/assets/2a9b1bd9-5469-4e69-9aed-612a4c025c69" />

## 기술 스택
### Backend & Library
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-007ACC?style=for-the-badge&logo=java&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-FF4081?style=for-the-badge&logo=java&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### Database
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Supabase](https://img.shields.io/badge/Supabase-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)
### Deployment
![Railway](https://img.shields.io/badge/Railway-0B0D0E?style=for-the-badge&logo=railway&logoColor=white)

### Collaboration
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Jira](https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white)
---

## 팀원별 구현 기능 상세
### 강현홍 - 대시 보드 관리
- 주요 지수 현황 요약
  * 즐겨찾기된 지수의 성과 정보 표시
- 지수 차트
  * 종가 기준 월/분기/년 단위 시계열 데이터
  * 이동평균선 데이터
- 지수 성과 분석 랭킹
  * 전일/전주/전월 대비 성과 랭킹
  * 종가 기준 비교

### 한성재 - 자동 연동 설정 관리
- 자동 연동 설정 등록
  * 지수의 데이터의 자동 연동 활성화 여부와 최근 연동 시간을 저장하는 기능 구현
- 자동 연동 설정 수정
  * 자동 연동 활성화/비활성화
  * 연동 시간 갱신
- 자동 연동 설정 목록 조회
  * 정렬 및 페이지네이션을 구현
  * 여러 정렬 조건 중 1개의 정렬 조건으로 조회
- 배치에 의한 지수 데이터 연동 자동화
  * 지수 데이터 연동 프로세스를 일정한 주기(1일)마다 자동으로 반복
  * Spring Scheduler를 활용해 구현
 
### 박지은 - 연동 작업 관리
- 지수 정보 연동
  * Open API를 활용해 지수 정보를 등록, 수정
- 지수 데이터 연동
  * Open API를 활용해 지수 데이터를 등록, 수정
- 연동 작업 목록 조회
  * 특정 조건으로 연동 작업 목록을 조회
  * 대상 날짜}, {작업일시}으로 정렬 및 페이지네이션을 구현

### 박성호 - 지수 정보 관리
- 지수 정보 등록
  * Open API를 활용해 자동으로 등록 가능
- 지수 정보 수정
  * {채용 종목 수}, {기준 시점}, {기준 지수}, {즐겨찾기}을 수정
- 지수 정보 삭제
- 지수 정보 목록 조회
  * {지수 분류명}, {지수명}, {즐겨찾기}로 지수 정보 목록을 조회
  * {지수 분류명}, {지수명}, {채용 종목 수}로 정렬 및 페이지네이션을 구현
 
### 임혜민 - 지수 데이터 관리
- 지수 데이터 등록
  * {지수}, {날짜}부터 {상장 시가 총액}까지 모든 속성을 입력해 지수 데이터를 등록
- 지수 데이터 수정
  * {지수}, {날짜}를 제외한 모든 속성을 수정
- 지수 데이터 삭제
- 지수 데이터 목록 조회
  * {지수}, {날짜}로 지수 데이터 목록을 조회
  * {소스 타입}을 제외한 모든 속성으로 정렬 및 페이지네이션을 구현
- 지수 데이터 Export
  * 지수 데이터를 CSV 파일로 Export

## 🏗 프로젝트 구조
```text
sb09-Findex-team02
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┣ java
 ┃ ┃ ┃ ┗ org.example
 ┃ ┃ ┃ ┃ ┣ api           
 ┃ ┃ ┃ ┃ ┣ client        
 ┃ ┃ ┃ ┃ ┣ config        
 ┃ ┃ ┃ ┃ ┣ controller    
 ┃ ┃ ┃ ┃ ┣ convert      
 ┃ ┃ ┃ ┃ ┣ dto          
 ┃ ┃ ┃ ┃ ┣ entity       
 ┃ ┃ ┃ ┃ ┣ exception    
 ┃ ┃ ┃ ┃ ┣ mapper        
 ┃ ┃ ┃ ┃ ┣ repository    
 ┃ ┃ ┃ ┃ ┣ scheduler     
 ┃ ┃ ┃ ┃ ┣ service       
 ┃ ┃ ┃ ┃ ┗ FindexApplication.java  
 ┃ ┗ resources           
 ┣ test           
 ┣ build.gradle        
 ┣ Procfile    
 ┗ README.md             
