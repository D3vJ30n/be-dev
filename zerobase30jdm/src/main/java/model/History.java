package model;

import java.sql.Timestamp;
import java.util.Objects;

public class History {
    // 위치 히스토리 테이블 필드 (ERD 기준)
    private int id;              // PK
    private double lat;          // X좌표(위도)
    private double lnt;          // Y좌표(경도)
    private Timestamp searchDttm;  // 조회일자

    public History() {}

    public History(double lat, double lnt) {
        this.lat = lat;
        this.lnt = lnt;
        this.searchDttm = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        validateLatitude(lat);
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        validateLongitude(lnt);
        this.lnt = lnt;
    }

    public Timestamp getSearchDttm() {
        return searchDttm;
    }

    public void setSearchDttm(Timestamp searchDttm) {
        this.searchDttm = searchDttm;
    }

    // 위도 유효성 검사 (위도 범위: -90 ~ 90)
    private void validateLatitude(double lat) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도는 -90도에서 90도 사이여야 합니다.");
        }
    }

    // 경도 유효성 검사 (경도 범위: -180 ~ 180)
    private void validateLongitude(double lnt) {
        if (lnt < -180 || lnt > 180) {
            throw new IllegalArgumentException("경도는 -180도에서 180도 사이여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;
        return id == history.id &&
            Double.compare(history.lat, lat) == 0 &&
            Double.compare(history.lnt, lnt) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lnt);
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", lat=" + lat +
            ", lnt=" + lnt +
            ", searchDttm=" + searchDttm +
            '}';
    }

    // 두 지점 간의 거리 계산 메서드 (단위: km)
    public double calculateDistance(double targetLat, double targetLnt) {
        // 지구의 반지름 (단위: km)
        final double EARTH_RADIUS = 6371.0;

        double dLat = Math.toRadians(targetLat - this.lat);
        double dLon = Math.toRadians(targetLnt - this.lnt);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(targetLat)) *
                Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
    }
}