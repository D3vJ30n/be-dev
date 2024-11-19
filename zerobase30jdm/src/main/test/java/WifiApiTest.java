package test;

import model.WifiApiResponse;
import org.junit.jupiter.api.Test;
import util.HttpClient;
import config.ApiProperties;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.Logger;

public class WifiApiTest {
    private static final Logger log = Logger.getLogger(WifiApiTest.class.getName());
    private static final ApiProperties apiConfig = ApiProperties.getInstance();

    @Test
    void wifiApiTest() {
        try {
            // API URL 구성
            String url = String.format("%s/%s/json/TbPublicWifiInfo/1/5",
                apiConfig.getBaseUrl(),
                apiConfig.getApiKey());

            // API 호출
            WifiApiResponse response = HttpClient.get(url, WifiApiResponse.class);

            // 결과 검증
            assertNotNull(response);
            assertNotNull(response.getWifiInfo());
            assertNotNull(response.getWifiInfo().getSpots());
            assertTrue(response.getWifiInfo().getSpots().size() > 0);

            // 첫 번째 와이파이 정보 출력
            log.info("첫 번째 와이파이 정보: " +
                response.getWifiInfo().getSpots().get(0).getName());

        } catch (Exception e) {
            log.severe("API 테스트 실패: " + e.getMessage());
            fail("API 테스트 실패: " + e.getMessage());
        }
    }
}