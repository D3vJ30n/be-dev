package controller;

import model.WifiInfo;
import service.WifiService;

import java.util.List;

/**
 * WifiController 클래스
 */
public class WifiController {
    private final WifiService wifiService = new WifiService();

    // 근처 Wi-Fi 검색
    public List<WifiInfo> getNearbyWifi(double lat, double lnt) {
        // getNearestWifi 메서드를 사용 (이 메서드는 내부적으로 기본 반경값을 사용)
        return wifiService.getNearestWifi(lat, lnt);
    }
}