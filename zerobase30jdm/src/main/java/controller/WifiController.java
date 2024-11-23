package controller;

import service.WifiService;

/**
 * WifiController 클래스
 * 와이파이 데이터를 처리하는 서비스 계층과 상호작용하는 컨트롤러 클래스
 * API 호출 및 데이터를 데이터베이스에 저장하는 로직을 관리
 */
public class WifiController {
    // WifiService 인스턴스를 초기화
    private final WifiService wifiService = new WifiService();

    /**
     * API 호출을 통해 와이파이 데이터를 가져오고, 데이터베이스에 저장
     * @return 저장된 와이파이 데이터 개수
     */
    public int loadWifiData() {
        return wifiService.fetchAndSaveWifiData();
    }
}
