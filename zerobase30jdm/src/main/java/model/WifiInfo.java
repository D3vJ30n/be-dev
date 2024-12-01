package model;

public class WifiInfo {

    private double distance;  // distance 필드 추가

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private String mgrNo;         // 와이파이 관리번호
    private String borough;       // 자치구
    private String name;          // 와이파이 이름
    private String address1;      // 도로명 주소
    private String address2;      // 상세 주소
    private String installFloor;  // 설치 위치(층)
    private String installType;   // 설치유형
    private String installAgency; // 설치기관
    private String serviceType;   // 서비스 구분
    private String networkType;   // 망종류
    private String installYear;   // 설치년도
    private String indoorOutdoor; // 실내외 구분
    private String remarks;       // 비고
    private double latitude;      // 위도
    private double longitude;     // 경도
    private String workDateTime;  // 작업일자

    // 생성자
    public WifiInfo() {}

    public WifiInfo(String mgrNo, String borough, String name, String address1, String address2,
                    String installFloor, String installType, String installAgency,
                    String serviceType, String networkType, String installYear,
                    String indoorOutdoor, String remarks, double latitude, double longitude, String workDateTime) {
        this.mgrNo = mgrNo;
        this.borough = borough;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.installFloor = installFloor;
        this.installType = installType;
        this.installAgency = installAgency;
        this.serviceType = serviceType;
        this.networkType = networkType;
        this.installYear = installYear;
        this.indoorOutdoor = indoorOutdoor;
        this.remarks = remarks;
        this.latitude = latitude;
        this.longitude = longitude;
        this.workDateTime = workDateTime;
    }

    // Getter와 Setter
    public String getMgrNo() { return mgrNo; }
    public void setMgrNo(String mgrNo) { this.mgrNo = mgrNo; }

    public String getBorough() { return borough; }
    public void setBorough(String borough) { this.borough = borough; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getInstallFloor() { return installFloor; }
    public void setInstallFloor(String installFloor) { this.installFloor = installFloor; }

    public String getInstallType() { return installType; }
    public void setInstallType(String installType) { this.installType = installType; }

    public String getInstallAgency() { return installAgency; }
    public void setInstallAgency(String installAgency) { this.installAgency = installAgency; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getNetworkType() { return networkType; }
    public void setNetworkType(String networkType) { this.networkType = networkType; }

    public String getInstallYear() { return installYear; }
    public void setInstallYear(String installYear) { this.installYear = installYear; }

    public String getIndoorOutdoor() { return indoorOutdoor; }
    public void setIndoorOutdoor(String indoorOutdoor) { this.indoorOutdoor = indoorOutdoor; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getWorkDateTime() { return workDateTime; }
    public void setWorkDateTime(String workDateTime) { this.workDateTime = workDateTime; }
}
