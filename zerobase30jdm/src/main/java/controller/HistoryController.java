package controller;

import model.History;
import service.HistoryService;
import java.util.List;

public class HistoryController {
    private final HistoryService historyService;

    public HistoryController() {
        this.historyService = new HistoryService();
    }

    public List<History> getHistoryList() {
        return historyService.getHistoryList();
    }

    public void addHistory(double lat, double lnt) {
        try {
            History history = new History();
            history.setLat(lat);
            history.setLnt(lnt);
            historyService.insertHistory(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteHistory(int id) {
        try {
            historyService.deleteHistory(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public History getHistory(int id) {
        return historyService.getHistory(id);
    }
}