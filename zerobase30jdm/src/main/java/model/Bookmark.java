package model;

import java.sql.Timestamp;

public class Bookmark {
    private int id;
    private int groupId;
    private String groupName;
    private String wifiId;
    private String wifiName;
    private Timestamp registerDate;

    public Bookmark() {}

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
        this.groupId = groupId;
    }

    public String getWifiId() {
        return wifiId;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
            "id=" + this.id +
            ", groupId=" + this.groupId +
            ", wifiId='" + this.wifiId + '\'' +
            ", wifiName='" + this.wifiName + '\'' +
            ", registerDate=" + this.registerDate +
            '}';
    }
}