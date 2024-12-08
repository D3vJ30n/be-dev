package controller;

import model.WifiInfo; // Wi-Fi 정보를 표현하는 데이터 모델 클래스
import service.WifiService; // Wi-Fi 관련 비즈니스 로직을 처리하는 서비스 클래스

import java.util.List; // 여러 Wi-Fi 정보를 관리하기 위한 List 컬렉션 클래스

/**
 * Wi-Fi 정보를 처리하는 컨트롤러 클래스.
 * 사용자의 위치 정보를 기반으로 가까운 Wi-Fi 정보를 검색하는 기능을 제공.
 */
public class WifiController {
    // Wi-Fi 관련 서비스 계층 객체 생성
    private final WifiService wifiService = new WifiService();

    /**
     * 사용자의 위치(위도와 경도)를 기반으로 가까운 Wi-Fi 정보를 검색.
     *
     * @param lat 사용자의 위도
     * @param lnt 사용자의 경도
     * @return 근처 Wi-Fi 정보 리스트
     */
    public List<WifiInfo> getNearbyWifi(double lat, double lnt) {
        // WifiService의 getNearestWifi 메서드를 호출하여 가까운 Wi-Fi 정보를 반환
        return wifiService.getNearestWifi(lat, lnt);
    }
}
