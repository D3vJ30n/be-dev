# 내 위치 기반 공공 와이파이 정보 제공 웹서비스

서울 공공 데이터 API를 활용하여 사용자가 현재 위치나 특정 위치를 기준으로 반경 1km 이내의 공공 와이파이를 검색하고, 검색 기록과 북마크를 관리할 수 있는 웹서비스입니다. 이 프로젝트는 API 연동, 데이터베이스 설계 및 연동, 그리고 Java 기반의 웹 애플리케이션 개발을 경험하기 위해 설계되었습니다.

---

## 주요 기능

1. **공공 와이파이 정보 조회**
   - 서울시 공공 데이터 API를 호출하여 공공 와이파이 정보를 가져옵니다.
   - 가져온 데이터를 MariaDB에 저장하여 효율적으로 관리하고, 필요한 정보를 검색합니다.

2. **위치 기반 와이파이 검색**
   - 사용자가 입력한 위도와 경도를 기준으로 반경 1km 이내의 공공 와이파이 정보를 검색합니다.
   - 최대 20개의 와이파이 정보를 거리 순으로 제공하며, 하버사인 공식을 사용하여 거리를 계산합니다.

3. **검색 히스토리 관리**
   - 사용자가 검색한 위치와 검색 날짜를 기록으로 저장합니다.
   - 저장된 검색 기록을 조회, 삭제할 수 있습니다.

4. **와이파이 상세 정보**
   - 특정 와이파이의 이름, 설치 위치, 설치 유형 등 상세 정보를 제공합니다.

5. **북마크 기능**
   - 북마크 그룹을 생성, 수정, 삭제할 수 있습니다.
   - 특정 와이파이를 북마크에 추가하거나 제거하여 즐겨찾는 와이파이를 손쉽게 관리할 수 있습니다.

---

## 기술 스택

- **프로그래밍 언어**: Java 8
- **빌드 도구**: Gradle
- **데이터베이스**: MariaDB
- **웹 서버**: Tomcat 9.0
- **프론트엔드**: JSP, HTML5, CSS
- **사용 라이브러리**
   - OkHttp3: API 호출 및 HTTP 요청 처리
   - Gson: JSON 데이터를 Java 객체로 변환
   - Lombok: Getter/Setter 자동 생성
   - JDBC: MariaDB와 Java 간 데이터 연동

---

## 데이터베이스 설계

### ERD (Entity Relationship Diagram)

이 프로젝트는 4개의 주요 테이블로 구성되어 있습니다.

1. **wifi_info (공공 와이파이 정보)**
   - **주요 필드**
      - `mgr_no`: 와이파이 고유 관리 번호 (Primary Key)
      - `main_nm`: 와이파이 이름
      - `adres1`: 도로명 주소
      - `lat`: 위도
      - `lnt`: 경도
   - **설명**: 서울 공공 데이터 API에서 가져온 와이파이 정보를 저장하는 테이블

2. **location_history (검색 기록)**
   - **주요 필드**
      - `id`: 고유 ID (Primary Key)
      - `lat`: 검색 시 입력한 위도
      - `lnt`: 검색 시 입력한 경도
      - `search_dttm`: 검색 시각
   - **설명**: 사용자가 와이파이 정보를 검색한 기록을 저장하는 테이블

3. **bookmark_group (북마크 그룹)**
   - **주요 필드**
      - `id`: 그룹 ID (Primary Key)
      - `name`: 그룹 이름
      - `order_no`: 그룹 정렬 순서
   - **설명**: 와이파이를 분류하기 위한 사용자 정의 북마크 그룹

4. **bookmark (북마크 데이터)**
   - **주요 필드**
      - `id`: 북마크 ID (Primary Key)
      - `group_id`: 북마크 그룹 ID (Foreign Key)
      - `wifi_mgr_no`: 와이파이 관리 번호 (Foreign Key)
   - **설명**: 특정 와이파이를 북마크에 저장한 정보를 관리

![Public WiFi Service Location Information System](images/Public%20WiFi%20Service%20Location%20Information%20System.png)

---

## 프로젝트 작동 방식

1. **와이파이 정보 가져오기**
   - 서울 공공 데이터 API에서 JSON 형태로 데이터를 가져옵니다.
   - 가져온 데이터를 Java 객체로 변환하고, MariaDB에 저장합니다.

2. **위치 기반 검색**
   - 사용자가 입력한 좌표를 기준으로 반경 1km 내 데이터를 조회합니다.
   - 하버사인 공식을 사용해 거리 계산을 수행합니다.

3. **히스토리 관리**
   - 사용자가 검색한 위치와 날짜를 `location_history` 테이블에 저장합니다.
   - 검색 기록은 필요 시 삭제할 수 있습니다.

4. **북마크 관리**
   - 사용자가 북마크 그룹을 만들고 와이파이를 추가, 삭제하며 즐겨찾는 와이파이를 관리합니다.

---

## 설치 및 실행 방법

### 1. 필수 소프트웨어 설치
- **JDK 1.8**: [다운로드](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- **MariaDB**: [다운로드](https://mariadb.org/)
- **Gradle**: [다운로드](https://gradle.org/releases/)
- **Tomcat 9.0**: [다운로드](https://tomcat.apache.org/)

### 2. 데이터베이스 설정
1. MariaDB를 설치하고 실행합니다.
2. `testdb1` 데이터베이스를 생성합니다.
   ```sql
   CREATE DATABASE testdb1;
   ```
3. MariaDB 사용자 이름과 비밀번호를 설정합니다.
4. 데이터베이스 연결 정보를 환경 변수로 설정합니다.
   ```bash
   export DB_URL="jdbc:mariadb://<IP주소>:3306/testdb1"
   export DB_USER="root"
   export DB_PASSWORD="<데이터베이스 비밀번호>"
   ```
5. `scripts/create_tables.sql` 파일에 있는 SQL 스크립트를 실행하여 테이블을 생성합니다.

### 3. 프로젝트 빌드 및 실행
1. Gradle로 프로젝트를 빌드합니다.
   ```bash
   gradle build
   ```
2. Tomcat 9.0 서버에 `.war` 파일을 배포합니다.
   - `build/libs` 디렉토리의 `.war` 파일을 Tomcat `webapps` 디렉토리에 복사합니다.
3. Tomcat 서버를 시작한 후, 웹 브라우저에서 프로젝트에 접속합니다.
   ```
   http://localhost:8080/{프로젝트 이름}
   ```

---

## API 키 설정
1. 서울 공공 데이터 API 포털에서 API 키를 발급받습니다: [서울 공공 데이터 API](https://data.seoul.go.kr/)
2. `WifiService` 클래스에서 `API_KEY` 값을 발급받은 키로 변경합니다.
   ```java
   private static final String API_KEY = "발급받은 API 키";
   ```

---

## 참고 사항
- 데이터베이스 초기 설정 파일 및 SQL 스크립트는 `scripts` 디렉토리에 있습니다.
- 프로젝트의 실행 및 설정과 관련해 문제가 발생하면 환경 변수 설정이나 API 키 확인을 우선적으로 점검하세요.