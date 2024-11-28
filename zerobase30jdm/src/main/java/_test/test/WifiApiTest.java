package _test.test;

import model.WifiSpot;
import service.WifiService;
import java.util.List;

public class WifiApiTest {
    public static void main(String[] args) {
        try {
            System.out.println("===== 테스트 시작 =====");

            WifiService wifiService = new WifiService();
            System.out.println("WifiService 초기화 성공");

            System.out.println("\n----- API 호출 및 데이터 저장 테스트 -----");
            int totalSaved = wifiService.fetchAndSaveWifiData();
            System.out.printf("저장된 데이터 개수: %d%n", totalSaved);

            System.out.println("\n----- DB 조회 테스트 -----");
            List<WifiSpot> wifiSpots = wifiService.getAllWifiSpots();
            System.out.printf("조회된 데이터 개수: %d%n", wifiSpots.size());

            System.out.println("\n===== 테스트 완료 =====");

        } catch (Exception e) {
            System.err.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}