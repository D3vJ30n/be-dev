package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * WifiSpot 클래스는 WiFi 정보를 담는 데이터 모델입니다.
 * 이 클래스는 JSON 응답에서 와이파이 정보 데이터를 매핑하는 데 사용됩니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // JSON 데이터에서 알려지지 않은 필드를 무시하도록 설정
public class WifiSpot {

    @JsonProperty("X_SWIFI_MGR_NO")  // JSON의 "X_SWIFI_MGR_NO" 속성과 매핑
    private String mgrNo;  // 와이파이 고유 식별 번호

    @JsonProperty("X_SWIFI_WRDOFC")  // JSON의 "X_SWIFI_WRDOFC" 속성과 매핑
    private String borough;  // 자치구 (소속된 지역)

    @JsonProperty("X_SWIFI_MAIN_NM")  // JSON의 "X_SWIFI_MAIN_NM" 속성과 매핑
    private String name;  // 와이파이 이름

    @JsonProperty("X_SWIFI_ADRES1")  // JSON의 "X_SWIFI_ADRES1" 속성과 매핑
    private String address1;  // 도로명주소

    @JsonProperty("X_SWIFI_ADRES2")  // JSON의 "X_SWIFI_ADRES2" 속성과 매핑
    private String address2;  // 상세주소

    @JsonProperty("X_SWIFI_INSTL_FLOOR")  // JSON의 "X_SWIFI_INSTL_FLOOR" 속성과 매핑
    private String installFloor;  // 설치 층

    @JsonProperty("X_SWIFI_INSTL_TY")  // JSON의 "X_SWIFI_INSTL_TY" 속성과 매핑
    private String installType;  // 설치 유형 (예: 실내, 실외)

    @JsonProperty("X_SWIFI_INSTL_MBY")  // JSON의 "X_SWIFI_INSTL_MBY" 속성과 매핑
    private String installAgency;  // 설치 기관

    @JsonProperty("X_SWIFI_SVC_SE")  // JSON의 "X_SWIFI_SVC_SE" 속성과 매핑
    private String serviceType;  // 서비스 구분 (예: 공공WiFi)

    @JsonProperty("X_SWIFI_CMCWR")  // JSON의 "X_SWIFI_CMCWR" 속성과 매핑
    private String networkType;  // 네트워크 종류

    @JsonProperty("X_SWIFI_CNSTC_YEAR")  // JSON의 "X_SWIFI_CNSTC_YEAR" 속성과 매핑
    private String installYear;  // 설치 연도

    @JsonProperty("X_SWIFI_INOUT_DOOR")  // JSON의 "X_SWIFI_INOUT_DOOR" 속성과 매핑
    private String indoorOutdoor;  // 실내/외 구분

    @JsonProperty("X_SWIFI_REMARS3")  // JSON의 "X_SWIFI_REMARS3" 속성과 매핑
    private String remarks;  // 비고 (추가적인 설명)

    @JsonProperty("LAT")  // JSON의 "LAT" 속성과 매핑
    private Double latitude;  // 위도 (Y 좌표)

    @JsonProperty("LNT")  // JSON의 "LNT" 속성과 매핑
    private Double longitude;  // 경도 (X 좌표)

    @JsonProperty("WORK_DTTM")  // JSON의 "WORK_DTTM" 속성과 매핑
    private String workDateTime;  // 작업 일시 (마지막 업데이트 시간)

    @JsonProperty("DISTANCE")  // JSON의 "DISTANCE" 속성과 매핑
    private Double distance;  // 거리 (Km 단위)
}
