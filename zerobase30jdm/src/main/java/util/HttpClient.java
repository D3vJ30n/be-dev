package util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build();

    public static String get(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("API 호출 실패: " + response.code());
                }
                return response.body().string();
            }
        } catch (Exception e) {
            throw new RuntimeException("HttpClient 호출 중 오류 발생", e);
        }
    }
}