package controller;

import model.History;
import service.HistoryService;
import java.util.List;

/**
 * HistoryController 클래스
 * 위치 히스토리와 관련된 로직을 처리하고 서비스 레이어와 상호작용하는 컨트롤러
 * 사용자 요청을 처리하고 서비스 메서드를 호출하여 적절한 응답을 반환
 */
public class HistoryController {
    private final HistoryService historyService;

    /**
     * 기본 생성자
     * HistoryService 인스턴스를 초기화
     */
    public HistoryController() {
        this.historyService = new HistoryService();
    }

    /**
     * 위치 히스토리 목록 조회
     * @return 위치 히스토리 목록
     */
    public List<History> getHistoryList() {
        try {
            return historyService.getHistoryList();
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 목록 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 위치 히스토리 추가
     * @param lat 위도
     * @param lnt 경도
     */
    public void addHistory(double lat, double lnt) {
        try {
            // 좌표 유효성 검사
            if (validateCoordinates(lat, lnt)) {
                History history = new History(lat, lnt);
                historyService.insertHistory(history); // DB에 히스토리 추가
            }
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 위치 좌표: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 추가 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 위치 히스토리 삭제
     * @param id 히스토리 ID
     */
    public void deleteHistory(int id) {
        try {
            // ID 유효성 검사
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            historyService.deleteHistory(id); // DB에서 히스토리 삭제
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 삭제 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 단일 위치 히스토리 조회
     * @param id 히스토리 ID
     * @return 조회된 위치 히스토리 객체
     */
    public History getHistory(int id) {
        try {
            // ID 유효성 검사
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            return historyService.getHistory(id); // ID로 히스토리 조회
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 전체 위치 히스토리 개수 조회
     * @return 위치 히스토리 총 개수
     */
    public int getHistoryCount() {
        try {
            return historyService.getHistoryCount(); // 히스토리 개수 반환
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 개수 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 좌표 유효성 검사
     * @param lat 위도
     * @param lnt 경도
     * @return 유효성 검사 결과 (true/false)
     */
    private boolean validateCoordinates(double lat, double lnt) {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도는 -90도에서 90도 사이여야 합니다.");
        }
        if (lnt < -180 || lnt > 180) {
            throw new IllegalArgumentException("경도는 -180도에서 180도 사이여야 합니다.");
        }
        return true;
    }
}
