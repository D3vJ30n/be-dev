package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WifiApiResponse {
    @JsonProperty("TbPublicWifiInfo")
    private WifiInfoWrapper wifiInfo;

    public WifiInfoWrapper getWifiInfo() {
        return wifiInfo;
    }

    public void setWifiInfo(WifiInfoWrapper wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    public static class WifiInfoWrapper {
        @JsonProperty("list_total_count")
        private int totalCount;

        @JsonProperty("row")
        private List<WifiSpot> spots; // WifiInfo를 WifiSpot으로 변경

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<WifiSpot> getSpots() { // 메서드명과 반환 타입 변경
            return spots;
        }

        public void setSpots(List<WifiSpot> spots) { // 파라미터 타입 변경
            this.spots = spots;
        }
    }
}