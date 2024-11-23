package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)  // JSON 데이터에서 알려지지 않은 필드를 무시하도록 설정
public class WifiApiResponse {
    @JsonProperty("TbPublicWifiInfo")  // JSON의 "TbPublicWifiInfo" 속성과 매핑
    private WifiInfo wifiInfo;  // WifiInfo 객체를 포함하는 필드

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)  // 내부 클래스에서 알려지지 않은 필드를 무시하도록 설정
    public static class WifiInfo {
        @JsonProperty("list_total_count")  // JSON의 "list_total_count" 속성과 매핑
        private Integer totalCount;  // 전체 와이파이 정보 개수를 담을 변수

        @JsonProperty("RESULT")  // JSON의 "RESULT" 속성과 매핑
        private ApiResult result;  // API 응답 상태 정보를 담을 변수

        @JsonProperty("row")  // JSON의 "row" 속성과 매핑
        private List<WifiSpot> spots;  // WifiSpot 객체들을 담는 리스트
    }
}
