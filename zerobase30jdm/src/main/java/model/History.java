package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * History 클래스
 * 위치 히스토리 데이터를 표현하는 모델 클래스
 * 위치 조회 기록과 관련된 정보를 저장하고 처리하는 역할을 담당
 */
public class History {
    // 위치 히스토리 테이블 필드 (ERD 기준)
    private int id;              // 위치 히스토리 ID (PK)
    private double lat;          // 위도 (X좌표)
    private double lnt;          // 경도 (Y좌표)
    private Timestamp searchDttm;  // 조회 일시

    /**
     * 기본 생성자
     */
    public History() {}

    /**
     * 주요 필드 초기화를 위한 생성자
     *
     * @param lat 위도
     * @param lnt 경도
     */
    public History(double lat, double lnt) {
        this.lat = lat;
        this.lnt = lnt;
        this.searchDttm = new Timestamp(System.currentTimeMillis());
    }

    /**
     * ID 가져오기
     * @return 위치 히스토리 ID
     */
    public int getId() {
        return id;
    }

    /**
     * ID 설정
     * @param id 위치 히스토리 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 위도 가져오기
     * @return 위도 (X좌표)
     */
    public double getLat() {
        return lat;
    }

    /**
     * 위도 설정
     *
     * @param lat 위도 값
     * @throws IllegalArgumentException 위도가 유효하지 않을 경우 예외 발생
     */
    public void setLat(double lat) {
        validateLatitude(lat);
        this.lat = lat;
    }

    /**
     * 경도 가져오기
     * @return 경도 (Y좌표)
     */
    public double getLnt() {
        return lnt;
    }

    /**
     * 경도 설정
     *
     * @param lnt 경도 값
     * @throws IllegalArgumentException 경도가 유효하지 않을 경우 예외 발생
     */
    public void setLnt(double lnt) {
        validateLongitude(lnt);
        this.lnt = lnt;
    }

    /**
     * 조회 일시 가져오기
     * @return 조회 일시
     */
    public Timestamp getSearchDttm() {
        return searchDttm;
    }

    /**
     * 조회 일시 설정
     *
     * @param searchDttm 조회 일시
     */
    public void setSearchDttm(Timestamp searchDttm) {
        this.searchDttm = searchDttm;
    }

    /**
     * 위도 유효성 검사
     * 위도 값이 -90 ~ 90 범위를 벗어나면 예외를 발생시킴
     *
     * @param lat 위도 값
     * @throws IllegalArgumentException 위도가 유효하지 않을 경우 예외 발생
     */
    private void validateLatitude(double lat) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도는 -90도에서 90도 사이여야 합니다.");
        }
    }

    /**
     * 경도 유효성 검사
     * 경도 값이 -180 ~ 180 범위를 벗어나면 예외를 발생시킴
     *
     * @param lnt 경도 값
     * @throws IllegalArgumentException 경도가 유효하지 않을 경우 예외 발생
     */
    private void validateLongitude(double lnt) {
        if (lnt < -180 || lnt > 180) {
            throw new IllegalArgumentException("경도는 -180도에서 180도 사이여야 합니다.");
        }
    }

    /**
     * 객체 비교를 위한 equals 메서드 오버라이드
     *
     * @param o 비교 대상 객체
     * @return 두 객체가 동일하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;
        return id == history.id &&
            Double.compare(history.lat, lat) == 0 &&
            Double.compare(history.lnt, lnt) == 0;
    }

    /**
     * 객체의 해시코드 반환
     * @return 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lnt);
    }

    /**
     * 객체 정보를 문자열로 반환
     * @return 객체 정보 문자열
     */
    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", lat=" + lat +
            ", lnt=" + lnt +
            ", searchDttm=" + searchDttm +
            '}';
    }

    /**
     * 두 지점 간의 거리 계산 메서드 (단위: km)
     * 하버사인 공식을 사용하여 두 지점 간의 거리를 계산
     *
     * @param targetLat 목표 지점의 위도
     * @param targetLnt 목표 지점의 경도
     * @return 두 지점 간의 거리 (km 단위)
     */
    public double calculateDistance(double targetLat, double targetLnt) {
        // 지구의 반지름 (단위: km)
        final double EARTH_RADIUS = 6371.0;

        // 위도 및 경도 차이를 라디안 값으로 변환
        double dLat = Math.toRadians(targetLat - this.lat);
        double dLon = Math.toRadians(targetLnt - this.lnt);

        // 하버사인 공식 적용
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(targetLat)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산
        return EARTH_RADIUS * c;
    }
}
