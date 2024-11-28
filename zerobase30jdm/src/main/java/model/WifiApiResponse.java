package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WifiApiResponse {
    @JsonProperty("TbPublicWifiInfo")
    private WifiInfoWrapper wifiInfo;

    public WifiInfoWrapper getWifiInfo() {
        return wifiInfo;
    }

    public void setWifiInfo(WifiInfoWrapper wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WifiInfoWrapper {
        @JsonProperty("list_total_count")
        private int totalCount;

        @JsonProperty("row")
        private List<WifiSpot> spots;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<WifiSpot> getSpots() {
            return spots;
        }

        public void setSpots(List<WifiSpot> spots) {
            this.spots = spots;
        }
    }
}