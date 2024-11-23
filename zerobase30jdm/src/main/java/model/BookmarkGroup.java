package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * BookmarkGroup 클래스
 * 북마크 그룹 데이터를 표현하는 모델 클래스
 * 북마크 그룹 테이블의 필드와 이를 다루기 위한 메서드 포함
 */
public class BookmarkGroup {
    // 북마크 그룹 테이블 필드 (ERD 기준)
    private int id;              // 북마크 그룹 ID (PK)
    private String name;         // 북마크 그룹 이름
    private int orderNo;         // 순서 (정렬을 위한 값)
    private Timestamp regDttm;   // 등록일자
    private Timestamp updDttm;   // 수정일자

    /**
     * 기본 생성자
     */
    public BookmarkGroup() {}

    /**
     * 주요 필드 초기화를 위한 생성자
     *
     * @param name 북마크 그룹 이름
     * @param orderNo 순서 값
     */
    public BookmarkGroup(String name, int orderNo) {
        this.name = name;
        this.orderNo = orderNo;
        this.regDttm = new Timestamp(System.currentTimeMillis());
    }

    /**
     * ID 가져오기
     * @return 북마크 그룹 ID
     */
    public int getId() {
        return id;
    }

    /**
     * ID 설정
     * @param id 북마크 그룹 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 그룹 이름 가져오기
     * @return 그룹 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 그룹 이름 설정
     *
     * @param name 그룹 이름
     * @throws IllegalArgumentException 그룹 이름이 null이거나 비어 있을 경우 예외 발생
     * @throws IllegalArgumentException 그룹 이름이 255자를 초과할 경우 예외 발생
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("그룹 이름은 필수값입니다.");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("그룹 이름은 255자를 초과할 수 없습니다.");
        }
        this.name = name.trim();
    }

    /**
     * 순서 값 가져오기
     * @return 순서 값
     */
    public int getOrderNo() {
        return orderNo;
    }

    /**
     * 순서 값 설정
     *
     * @param orderNo 순서 값
     * @throws IllegalArgumentException 순서 값이 0보다 작을 경우 예외 발생
     */
    public void setOrderNo(int orderNo) {
        if (orderNo < 0) {
            throw new IllegalArgumentException("순서는 0 이상이어야 합니다.");
        }
        this.orderNo = orderNo;
    }

    /**
     * 등록일자 가져오기
     * @return 등록일자
     */
    public Timestamp getRegDttm() {
        return regDttm;
    }

    /**
     * 등록일자 설정
     * @param regDttm 등록일자
     */
    public void setRegDttm(Timestamp regDttm) {
        this.regDttm = regDttm;
    }

    /**
     * 수정일자 가져오기
     * @return 수정일자
     */
    public Timestamp getUpdDttm() {
        return updDttm;
    }

    /**
     * 수정일자 설정
     * @param updDttm 수정일자
     */
    public void setUpdDttm(Timestamp updDttm) {
        this.updDttm = updDttm;
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

        BookmarkGroup that = (BookmarkGroup) o;
        return id == that.id &&
            Objects.equals(name, that.name);
    }

    /**
     * 객체의 해시코드 반환
     * @return 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * 객체 정보를 문자열로 반환
     * @return 객체 정보 문자열
     */
    @Override
    public String toString() {
        return "BookmarkGroup{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", orderNo=" + orderNo +
            ", regDttm=" + regDttm +
            ", updDttm=" + updDttm +
            '}';
    }
}
