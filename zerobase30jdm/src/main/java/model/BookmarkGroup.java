package model;

import java.sql.Timestamp;
import java.util.Objects;

public class BookmarkGroup {
    // 북마크 그룹 테이블 필드 (ERD 기준)
    private int id;              // PK
    private String name;         // 북마크 그룹 이름
    private int orderNo;         // 순서
    private Timestamp regDttm;   // 등록일자
    private Timestamp updDttm;   // 수정일자

    public BookmarkGroup() {}

    public BookmarkGroup(String name, int orderNo) {
        this.name = name;
        this.orderNo = orderNo;
        this.regDttm = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("그룹 이름은 필수값입니다.");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("그룹 이름은 255자를 초과할 수 없습니다.");
        }
        this.name = name.trim();
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        if (orderNo < 0) {
            throw new IllegalArgumentException("순서는 0 이상이어야 합니다.");
        }
        this.orderNo = orderNo;
    }

    public Timestamp getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(Timestamp regDttm) {
        this.regDttm = regDttm;
    }

    public Timestamp getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(Timestamp updDttm) {
        this.updDttm = updDttm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookmarkGroup that = (BookmarkGroup) o;
        return id == that.id &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

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