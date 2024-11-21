package study.backend.studying.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Main {
    public <Amoeba> int solution(int delay, int N) {
        // 결과를 저장할 set
        List<Integer> result = new ArrayList<>();

        // 현재 살아있는 아메바들을 저장할 큐
        Queue<Integer> amoebas = new LinkedList<>();

        //초기 아메바 생성
        Amoeba amoeba = new Amoeba(1, 0, delay);


        return 0;
    }
}