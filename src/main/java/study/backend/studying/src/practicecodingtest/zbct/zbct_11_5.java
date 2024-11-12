package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_11_5 {
    public int solution(int N, int M, int K, int[] capacity) {                                     // 메인 솔루션 함수 시작
        // N: 전체 학생 수
        // M: 교실 수
        // K: 감독관 수
        // capacity: 각 교실의 최대 수용 가능 인원이 담긴 배열
        int[] classRooms = new int[M];  // 각 교실에 실제로 배치된 학생 수를 저장할 배열
        return distribute(0, N, classRooms, capacity, K, N);                                       // 첫 번째 교실(0)부터 시작하여 학생 배치 시작
    }

    int distribute(int room, int remainStudents, int[] classRooms, int[] maxStudent, int supervisors, int total) {  // 학생 배치 함수
        // room: 현재 처리중인 교실 번호
        // remainStudents: 아직 배치되지 않은 남은 학생 수
        // classRooms: 각 교실별 배치된 학생 수 배열
        // maxStudent: 각 교실별 최대 수용 가능 학생 수 배열
        // supervisors: 전체 감독관 수
        // total: 전체 학생 수

        // 모든 교실에 대한 학생 배치가 완료된 경우
        if (room == classRooms.length) {                                                          // 현재 교실이 전체 교실 수와 같으면 (모든 교실 처리 완료)
            // 모든 학생이 배치되었는지 확인
            if (remainStudents == 0) {                                                            // 남은 학생이 0명이면
                return calculateAll(classRooms, supervisors, total);                              // 전체 경우의 수 계산
            }
            return 0;  // 학생이 남았다면 유효하지 않은 경우                                           // 남은 학생이 있다면 실패
        }

        int cases = 0;  // 가능한 총 경우의 수                                                       // 현재 교실에서의 가능한 경우의 수 저장 변수
        // 현재 교실에 배치할 수 있는 최대 학생 수 (남은 학생 수와 교실 수용 가능 인원 중 작은 값)
        int max = Math.min(remainStudents, maxStudent[room]);                                     // 현재 교실에 배치 가능한 최대 학생 수 계산

        // 현재 교실에 0명부터 최대 인원까지 배치해보며 모든 경우의 수 시도
        for (int i = 0; i <= max; i++) {                                                         // 0명부터 최대 가능 인원까지 반복
            classRooms[room] = i;  // 현재 교실에 i명 배치                                           // 현재 교실에 i명 배정
            cases += distribute(room + 1, remainStudents - i, classRooms, maxStudent, supervisors, total);  // 다음 교실 처리
        }
        return cases;                                                                            // 현재 교실에서의 모든 경우의 수 반환
    }

    int calculateAll(int[] classRooms, int supervisors, int totalStudents) {                     // 전체 경우의 수 계산 함수
        // 학생들을 각 교실에 나누는 경우의 수 계산
        long studentWays = factorial(totalStudents);  // 전체 학생의 순열                            // 전체 학생 수의 팩토리얼 계산
        for (int count : classRooms) {                                                           // 각 교실을 순회하며
            if (count > 0) {                                                                     // 학생이 배치된 교실에 대해
                studentWays /= factorial(count);  // 각 교실 내의 학생 순서는 구분하지 않음              // 해당 교실 학생 수의 팩토리얼로 나눔
            }
        }

        // 감독관을 각 교실에 배치하는 경우의 수 계산 (순열)
        long supervisorWays = 1;                                                                 // 감독관 배치 경우의 수 초기값
        for (int i = 0; i < classRooms.length; i++) {                                           // 교실 수만큼 반복
            supervisorWays *= (supervisors - i);  // K * (K-1) * (K-2) ... 계산                   // 남은 감독관 수를 곱함
        }

        // 전체 경우의 수 = 학생 배치 경우의 수 × 감독관 배치 경우의 수
        return (int)(studentWays * supervisorWays);                                             // 학생 배치와 감독관 배치 경우의 수를 곱해서 반환
    }

    long factorial(int n) {                                                                      // 팩토리얼 계산 함수
        // n! 계산 (1부터 n까지의 곱)
        long result = 1;                                                                         // 결과 저장 변수 초기화
        for (int i = 2; i <= n; i++) {                                                          // 2부터 n까지 반복
            result *= i;                                                                         // i를 곱함
        }
        return result;                                                                          // 계산된 팩토리얼 값 반환
    }
}