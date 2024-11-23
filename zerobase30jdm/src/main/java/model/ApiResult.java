package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * ApiResult 클래스
 * API 응답 결과를 표현하는 데이터 모델 클래스
 * API 호출의 상태 코드 및 메시지를 포함
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // JSON 데이터에서 알려지지 않은 필드는 무시
public class ApiResult {

    /**
     * API 응답 코드 (e.g., 성공/실패를 나타내는 코드)
     */
    @JsonProperty("CODE")
    private String code;

    /**
     * API 응답 메시지 (e.g., 상태에 대한 상세 설명)
     */
    @JsonProperty("MESSAGE")
    private String message;
}
