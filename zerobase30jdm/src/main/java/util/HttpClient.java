package util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    // OkHttpClient 객체를 생성하고, 연결 및 읽기 타임아웃을 60초로 설정
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)  // 연결 타임아웃 60초
        .readTimeout(60, TimeUnit.SECONDS)     // 읽기 타임아웃 60초
        .build();

    // 주어진 URL에 GET 요청을 보내고 응답을 반환하는 메서드
    public static String get(String url) {
        try {
            // GET 요청 생성
            Request request = new Request.Builder().url(url).get().build();

            // 요청을 보내고 응답을 처리
            try (Response response = client.newCall(request).execute()) {
                // 응답 상태 코드가 성공적이지 않으면 예외를 발생시킴
                if (!response.isSuccessful()) {
                    throw new RuntimeException("API 호출 실패: " + response.code());
                }
                // 응답 본문을 문자열로 반환
                return response.body().string();
            }
        } catch (Exception e) {
            // 예외가 발생하면 메시지를 출력하고 예외를 던짐
            throw new RuntimeException("HttpClient 호출 중 오류 발생", e);
        }
    }
}
