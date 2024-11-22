package controller;

import service.WifiService;

public class WifiController {
    private final WifiService wifiService = new WifiService();

    // API 호출 및 저장 결과 반환
    public int loadWifiData() {
        return wifiService.fetchAndSaveWifiData();
    }
}
