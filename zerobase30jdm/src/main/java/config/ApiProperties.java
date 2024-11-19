package config;

import lombok.AccessLevel;
import lombok.Getter;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;  // Java 기본 로거 사용

@Getter
public class ApiProperties {
    private static final Logger log = Logger.getLogger(ApiProperties.class.getName());  // 로거 직접 선언
    private static final ApiProperties instance = new ApiProperties();

    @Getter(AccessLevel.NONE)
    private final Properties properties = new Properties();

    private String apiKey;
    private String baseUrl;

    private ApiProperties() {
        load();
    }

    public static ApiProperties getInstance() {
        return instance;
    }

    private void load() {
        try (InputStream input = getClass().getClassLoader()
            .getResourceAsStream("api.properties")) {
            if (input == null) {
                throw new RuntimeException("api.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
            this.apiKey = properties.getProperty("api.key");
            this.baseUrl = properties.getProperty("api.url");
        } catch (Exception e) {
            log.severe("API 설정 로드 실패: " + e.getMessage());  // error() 대신 severe() 사용
            throw new RuntimeException("API 설정 로드 실패", e);
        }
    }
}