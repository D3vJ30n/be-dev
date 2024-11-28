package study.backend.studying.src.practicecodingtest.zbct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class zbct_13_4 {
    public int[] solution(int[] start, int[] time) {
        int n = start.length; // 작업의 개수
        int[] result = new int[n]; // 결과를 저장할 배열 (작업이 완료된 순서대로 작업 인덱스가 저장됨)

        List<int[]> jobs = new ArrayList<>(); // 작업 정보를 저장할 리스트 (start, time, index)
        for (int i = 0; i < n; i++) {
            jobs.add(new int[] {start[i], time[i], i}); // 각 작업을 배열로 만들어 리스트에 추가
        }

        // 작업 리스트를 시작 시간(start) 기준으로 오름차순 정렬
        jobs.sort(Comparator.comparingInt(a -> a[0]));

        int currentTime = 0; // 현재 시간
        int completed = 0; // 완료된 작업의 개수
        int jobIndex = 0; // 작업 리스트에서 아직 처리하지 않은 작업의 인덱스

        // 소요 시간(time) 기준으로 작업을 우선순위 큐에 저장
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1]; // 소요 시간이 짧은 작업이 우선
            }
            return a[2] - b[2]; // 소요 시간이 같으면 작업의 인덱스가 작은 작업이 우선
        });

        // 모든 작업을 완료할 때까지 반복
        while (completed < n) {
            // 현재 시간보다 시작 시간이 작거나 같은 작업을 우선순위 큐에 추가
            while (jobIndex < n && jobs.get(jobIndex)[0] <= currentTime) {
                pq.add(jobs.get(jobIndex)); // 작업을 큐에 추가
                jobIndex++; // 다음 작업으로 이동
            }

            if (!pq.isEmpty()) {
                // 우선순위 큐에서 가장 우선순위가 높은 작업(소요 시간이 가장 짧은 작업)을 가져옴
                int[] job = pq.poll();
                result[completed++] = job[2]; // 해당 작업의 인덱스를 결과 배열에 저장
                currentTime += job[1]; // 현재 시간을 작업의 소요 시간만큼 증가
            } else {
                // 큐가 비어 있으면, 현재 시간보다 뒤에 시작하는 작업으로 시간을 이동
                currentTime = jobs.get(jobIndex)[0];
            }
        }

        return result; // 결과 배열 반환
    }
}
