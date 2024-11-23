package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Bookmark 클래스
 * 북마크 데이터를 표현하는 모델 클래스
 * 북마크 테이블의 필드와 JOIN 조회용 필드를 포함
 */
public class Bookmark {

    // 북마크 테이블 필드 (ERD 기준)
    private int id;              // 북마크 ID (PK)
    private int groupId;         // 북마크 그룹 ID (FK - bookmark_group 참조)
    private String wifiMgrNo;    // 와이파이 관리번호 (FK - wifi_info 참조)
    private Timestamp regDttm;   // 등록일시

    // JOIN 조회용 필드
    private String groupName;    // 북마크 그룹 이름 (bookmark_group의 name 필드)
    private String wifiName;     // 와이파이 이름 (wifi_info의 main_nm 필드)

    /**
     * 기본 생성자
     */
    public Bookmark() {}

    /**
     * 주요 필드 초기화를 위한 생성자
     *
     * @param groupId 북마크 그룹 ID
     * @param wifiMgrNo 와이파이 관리번호
     */
    public Bookmark(int groupId, String wifiMgrNo) {
        this.groupId = groupId;
        this.wifiMgrNo = wifiMgrNo;
        this.regDttm = new Timestamp(System.currentTimeMillis());
    }

    /**
     * ID 가져오기
     * @return 북마크 ID
     */
    public int getId() {
        return id;
    }

    /**
     * ID 설정
     * @param id 북마크 ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 북마크 그룹 ID 가져오기
     * @return 북마크 그룹 ID
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * 북마크 그룹 ID 설정
     *
     * @param groupId 북마크 그룹 ID
     * @throws IllegalArgumentException 그룹 ID가 0 이하일 경우 예외 발생
     */
    public void setGroupId(int groupId) {
        if (groupId <= 0) {
            throw new IllegalArgumentException("북마크 그룹 ID는 0보다 커야 합니다.");
        }
        this.groupId = groupId;
    }

    /**
     * 와이파이 관리번호 가져오기
     * @return 와이파이 관리번호
     */
    public String getWifiMgrNo() {
        return wifiMgrNo;
    }

    /**
     * 와이파이 관리번호 설정
     *
     * @param wifiMgrNo 와이파이 관리번호
     * @throws IllegalArgumentException 관리번호가 null이거나 비어 있으면 예외 발생
     */
    public void setWifiMgrNo(String wifiMgrNo) {
        if (wifiMgrNo == null || wifiMgrNo.trim().isEmpty()) {
            throw new IllegalArgumentException("와이파이 관리번호는 필수값입니다.");
        }
        this.wifiMgrNo = wifiMgrNo.trim();
    }

    /**
     * 등록일시 가져오기
     * @return 등록일시
     */
    public Timestamp getRegDttm() {
        return regDttm;
    }

    /**
     * 등록일시 설정
     * @param regDttm 등록일시
     */
    public void setRegDttm(Timestamp regDttm) {
        this.regDttm = regDttm;
    }

    /**
     * 북마크 그룹 이름 가져오기
     * @return 북마크 그룹 이름
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 북마크 그룹 이름 설정
     * @param groupName 북마크 그룹 이름
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 와이파이 이름 가져오기
     * @return 와이파이 이름
     */
    public String getWifiName() {
        return wifiName;
    }

    /**
     * 와이파이 이름 설정
     * @param wifiName 와이파이 이름
     */
    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
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

        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
            groupId == bookmark.groupId &&
            Objects.equals(wifiMgrNo, bookmark.wifiMgrNo);
    }

    /**
     * 객체의 해시코드 반환
     * @return 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, wifiMgrNo);
    }

    /**
     * 객체 정보를 문자열로 반환
     * @return 객체 정보 문자열
     */
    @Override
    public String toString() {
        return "Bookmark{" +
            "id=" + id +
            ", groupId=" + groupId +
            ", wifiMgrNo='" + wifiMgrNo + '\'' +
            ", regDttm=" + regDttm +
            ", groupName='" + groupName + '\'' +
            ", wifiName='" + wifiName + '\'' +
            '}';
    }
}
