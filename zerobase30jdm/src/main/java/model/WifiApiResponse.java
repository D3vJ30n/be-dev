package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WifiApiResponse {
    @JsonProperty("TbPublicWifiInfo")
    private WifiInfo wifiInfo;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WifiInfo {
        @JsonProperty("list_total_count")
        private Integer totalCount;

        @JsonProperty("RESULT")
        private ApiResult result;

        @JsonProperty("row")
        private List<WifiSpot> spots;
    }
}