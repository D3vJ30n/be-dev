package study.backend.studying.src.algorithm.streamapi;

// 필요한 라이브러리들을 가져옴
// ArrayList: 동적 배열을 사용하기 위한 클래스
// Comparator: 정렬을 위한 인터페이스
// List: 리스트 인터페이스
// Collectors: 스트림 결과를 수집하기 위한 클래스
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 우주 물체의 정보를 저장하는 클래스
// 각 우주 물체는 type(종류)과 dist(거리)를 가짐
class SpaceObject {
    String type;    // 물체의 종류 (planet, star, milkyway)
    int dist;       // 물체와의 거리 (양수: 앞쪽, 음수: 뒤쪽)

    // 생성자: 새로운 우주 물체를 만들 때 호출됨
    // type과 dist를 받아서 객체의 필드를 초기화
    SpaceObject(String type, int dist) {
        this.type = type;  // this.type은 클래스의 필드, type은 매개변수
        this.dist = dist;  // this.dist는 클래스의 필드, dist는 매개변수
    }
}

public class streamapiex {
    public static List<Integer> solution(List<SpaceObject> arr) {
        return arr.stream()   // 리스트를 스트림으로 변환 (스트림: 데이터의 흐름)
            // filter: 조건에 맞는 데이터만 선택
            // obj는 각각의 SpaceObject를 의미
            // obj.type이 "planet"과 같은 것만 선택
            .filter(obj -> obj.type.equals("planet"))

            // map: 데이터를 변환
            // SpaceObject에서 거리(dist) 정보만 추출
            // obj.dist는 각 행성의 거리값
            .map(obj -> obj.dist)

            // sorted: 데이터 정렬
            // (a, b) -> Math.abs(a) - Math.abs(b)는 정렬 기준
            // Math.abs()는 절대값을 구하는 함수
            // 거리의 절대값이 작은 순서대로 정렬 (가까운 순)
            .sorted((a, b) -> Math.abs(a) - Math.abs(b))

            // limit: 앞에서부터 지정된 개수만큼만 선택
            // 여기서는 가장 가까운 3개만 선택
            .limit(3)

            // collect: 스트림의 결과를 원하는 형태로 변환
            // Collectors.toList()는 결과를 List 형태로 만듦
            .collect(Collectors.toList());
    }

    // 메인 메서드: 프로그램의 시작점
    public static void main(String[] args) {
        // SpaceObject들을 저장할 리스트 생성
        List<SpaceObject> arr = new ArrayList<>();

        // 리스트에 여러 우주 물체 추가
        // new SpaceObject()로 새로운 우주 물체 객체 생성
        arr.add(new SpaceObject("planet", 10));    // 행성, 앞으로 10만큼
        arr.add(new SpaceObject("star", -3));      // 별, 뒤로 3만큼
        arr.add(new SpaceObject("milkyway", 2));   // 은하수, 앞으로 2만큼
        arr.add(new SpaceObject("planet", 5));     // 행성, 앞으로 5만큼
        arr.add(new SpaceObject("planet", -7));    // 행성, 뒤로 7만큼
        arr.add(new SpaceObject("star", 4));       // 별, 앞으로 4만큼
        arr.add(new SpaceObject("planet", 6));     // 행성, 앞으로 6만큼
        arr.add(new SpaceObject("planet", -1));    // 행성, 뒤로 1만큼
        arr.add(new SpaceObject("star", 8));       // 별, 앞으로 8만큼
        arr.add(new SpaceObject("milkyway", -9));  // 은하수, 뒤로 9만큼

        // solution 메서드를 호출하여 결과 받기
        // 결과는 가장 가까운 3개의 행성 거리가 담긴 리스트
        List<Integer> result = solution(arr);

        // 결과 출력
        // [-1, 5, 6] 이런 식으로 출력됨
        System.out.println(result);
    }
}