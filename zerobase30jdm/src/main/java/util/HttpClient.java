package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final Logger log = Logger.getLogger(HttpClient.class.getName());
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build();


    public static <T> T get(String url, Class<T> responseType) {
        try {
            Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("API 호출 실패: " + response.code());
                }
                String responseBody = response.body().string();
                return new ObjectMapper().readValue(responseBody, responseType);
            }
        } catch (Exception e) {
            log.severe("API 호출 중 에러 발생: " + e.getMessage());  // error() 대신 severe() 사용
            throw new RuntimeException("API 호출 실패", e);
        }
    }
}