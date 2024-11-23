package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * WifiSpot 클래스는 WiFi 정보를 담는 데이터 모델입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // JSON 데이터에서 알려지지 않은 필드 무시
public class WifiSpot {
    @JsonProperty("X_SWIFI_MGR_NO")
    private String mgrNo;

    @JsonProperty("X_SWIFI_WRDOFC")
    private String borough;

    @JsonProperty("X_SWIFI_MAIN_NM")
    private String name;

    @JsonProperty("X_SWIFI_ADRES1")
    private String address1;

    @JsonProperty("X_SWIFI_ADRES2")
    private String address2;

    @JsonProperty("X_SWIFI_INSTL_FLOOR")
    private String installFloor;

    @JsonProperty("X_SWIFI_INSTL_TY")
    private String installType;

    @JsonProperty("X_SWIFI_INSTL_MBY")
    private String installAgency;

    @JsonProperty("X_SWIFI_SVC_SE")
    private String serviceType;

    @JsonProperty("X_SWIFI_CMCWR")
    private String networkType;

    @JsonProperty("X_SWIFI_CNSTC_YEAR")
    private String installYear;

    @JsonProperty("X_SWIFI_INOUT_DOOR")
    private String indoorOutdoor;

    @JsonProperty("X_SWIFI_REMARS3")
    private String remarks;

    @JsonProperty("LAT")
    private Double latitude;

    @JsonProperty("LNT")
    private Double longitude;

    @JsonProperty("WORK_DTTM")
    private String workDateTime;
}
