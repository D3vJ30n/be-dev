package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WifiSpot {
    @JsonProperty("X_SWIFI_MGR_NO")
    private String managementNo;

    @JsonProperty("X_SWIFI_WRDOFC")
    private String borough;

    @JsonProperty("X_SWIFI_MAIN_NM")
    private String name;

    @JsonProperty("X_SWIFI_ADRES1")
    private String address1;

    @JsonProperty("X_SWIFI_ADRES2")
    private String address2;

    @JsonProperty("X_SWIFI_INSTL_TY")
    private String installType;

    @JsonProperty("X_SWIFI_INSTL_FLOOR")
    private String installFloor;

    @JsonProperty("X_SWIFI_INSTL_MBY")
    private String installAgency;

    @JsonProperty("X_SWIFI_LAT")
    private Double latitude;

    @JsonProperty("X_SWIFI_LNT")
    private Double longitude;

    @JsonProperty("WORK_DTTM")
    private String workDateTime;
}