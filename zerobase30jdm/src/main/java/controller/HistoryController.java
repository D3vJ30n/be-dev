package controller;

import model.History;
import service.HistoryService;
import java.util.List;

public class HistoryController {
    private final HistoryService historyService;

    public HistoryController() {
        this.historyService = new HistoryService();
    }

    // 위치 히스토리 목록 조회
    public List<History> getHistoryList() {
        try {
            return historyService.getHistoryList();
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 목록 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 위치 히스토리 추가
    public void addHistory(double lat, double lnt) {
        try {
            if (validateCoordinates(lat, lnt)) {
                History history = new History(lat, lnt);
                historyService.insertHistory(history);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 위치 좌표: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 추가 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 위치 히스토리 삭제
    public void deleteHistory(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            historyService.deleteHistory(id);
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 삭제 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 단일 히스토리 조회
    public History getHistory(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            return historyService.getHistory(id);
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 전체 히스토리 개수 조회
    public int getHistoryCount() {
        try {
            return historyService.getHistoryCount();
        } catch (RuntimeException e) {
            System.err.println("위치 히스토리 개수 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    // 좌표 유효성 검사
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