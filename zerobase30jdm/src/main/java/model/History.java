package model;

import java.sql.Timestamp;

public class History {
    private int id;
    private double lat;
    private double lnt;
    private Timestamp searchDate;

    public History() {}

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
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public Timestamp getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Timestamp searchDate) {
        this.searchDate = searchDate;
    }
}