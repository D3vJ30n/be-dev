package _test.test;

import model.WifiApiResponse;
import model.WifiSpot;
import service.WifiService;

import java.util.List;

public class DBTest {
    public static void main(String[] args) {
        try {
            System.out.println("===== 테스트 시작 =====");

            // WifiService 객체 생성
            WifiService wifiService = new WifiService();
            System.out.println("WifiService 초기화 성공");

            // 1. API 호출 테스트
            System.out.println("\n----- API 호출 테스트 -----");
            WifiApiResponse response = wifiService.getWifiInfoFromApi(1, 1000);
            System.out.println("API 응답 성공!");
            System.out.println("총 데이터 수: " + response.getWifiInfo().getTotalCount());

            // 2. DB 저장 테스트
            System.out.println("\n----- DB 저장 테스트 -----");
            wifiService.saveWifiSpots(response.getWifiInfo().getSpots());
            System.out.println("데이터 저장 성공!");

            // 3. DB 조회 테스트
            System.out.println("\n----- DB 조회 테스트 -----");
            List<WifiSpot> wifiSpots = wifiService.getAllWifiSpots();
            System.out.println("조회된 데이터 개수: " + wifiSpots.size());

            System.out.println("\n===== 테스트 완료 =====");

        } catch (Exception e) {
            System.err.println("\n!!!!!!! 에러 발생 !!!!!!!");
            System.err.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
        }
    }
}