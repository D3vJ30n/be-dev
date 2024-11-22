package model;

import java.sql.Timestamp;
import java.util.Objects;

public class Bookmark {
    // 북마크 테이블 필드 (ERD 기준)
    private int id;              // PK
    private int groupId;         // FK - bookmark_group 참조
    private String wifiMgrNo;    // FK - wifi_info 참조
    private Timestamp regDttm;   // 등록일시

    // JOIN 조회용 필드
    private String groupName;    // bookmark_group의 name
    private String wifiName;     // wifi_info의 main_nm

    public Bookmark() {}

    public Bookmark(int groupId, String wifiMgrNo) {
        this.groupId = groupId;
        this.wifiMgrNo = wifiMgrNo;
        this.regDttm = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        if (groupId <= 0) {
            throw new IllegalArgumentException("북마크 그룹 ID는 0보다 커야 합니다.");
        }
        this.groupId = groupId;
    }

    public String getWifiMgrNo() {
        return wifiMgrNo;
    }

    public void setWifiMgrNo(String wifiMgrNo) {
        if (wifiMgrNo == null || wifiMgrNo.trim().isEmpty()) {
            throw new IllegalArgumentException("와이파이 관리번호는 필수값입니다.");
        }
        this.wifiMgrNo = wifiMgrNo.trim();
    }

    public Timestamp getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(Timestamp regDttm) {
        this.regDttm = regDttm;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
            groupId == bookmark.groupId &&
            Objects.equals(wifiMgrNo, bookmark.wifiMgrNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, wifiMgrNo);
    }

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