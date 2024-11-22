package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final Logger log = Logger.getLogger(HttpClient.class.getName()); // Logger 객체 생성
    private static final OkHttpClient client = new OkHttpClient.Builder() // OkHttpClient 객체 생성
        .connectTimeout(60, TimeUnit.SECONDS) // 60초 동안 서버로부터 응답을 기다림
        .readTimeout(60, TimeUnit.SECONDS) // 60초 동안 서버로부터 응답을 읽음
        .build(); // OkHttpClient 객체 생성


    public static <T> T get(String url, Class<T> responseType) { // 제네릭 메서드로 수정
        try {
            Request request = new Request.Builder() // Request 객체 생성
                .url(url) // 요청 URL 설정
                .get() // GET 메서드로 요청
                .build(); // Request 객체 생성

            try (Response response = client.newCall(request).execute()) { // OkHttpClient 객체로 요청을 보내고 응답을 받음
                if (!response.isSuccessful()) { // 응답이 성공적이지 않은 경우
                    throw new RuntimeException("API 호출 실패: " + response.code()); // 예외 발생
                }
                String responseBody = response.body().string(); // 응답 바디를 문자열로 변환
                return new ObjectMapper().readValue(responseBody, responseType); // JSON 문자열을 Java 객체로 변환하여 반환
            }
        } catch (Exception e) { // 예외 발생 시
            log.severe("API 호출 중 에러 발생: " + e.getMessage());  // error() 대신 severe() 사용
            throw new RuntimeException("API 호출 실패", e); // 예외 발생
        }
    }
}