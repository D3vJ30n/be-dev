//package study.backend.studying.src.zbmissions;  // 패키지 선언: 코드의 네임스페이스를 지정하여 코드 구조화
//
///*
// * 제로베이스 백엔드 스쿨 30기
// * 전도명
// *
// * Mission 1 깜짝과제 2. 좌표 거리 계산 프로그램
// *
// */
//
//// java.awt.Point 클래스를 사용하여 x, y 좌표를 쉽게 관리
//import java.awt.*;
//// LinkedHashSet: 입력 순서를 보장하면서 중복을 허용하지 않는 컬렉션
//import java.util.LinkedHashSet;
//// 사용자 입력을 받기 위한 Scanner 클래스
//import java.util.Scanner;
//// 중복되지 않는 좌표들을 저장하기 위한 Set 인터페이스
//import java.util.Set;
//
//// 좌표 거리 계산 프로그램
//// 사용자의 좌표와 여러 개의 임의 좌표들 간의 거리를 계산하여
//// 가장 가까운 좌표를 찾아 출력
//// 좌표 거리 계산 프로그램
//// 사용자의 좌표와 여러 개의 임의 좌표들 간의 거리를 계산하여
//// 가장 가까운 좌표를 찾아 출력
//public class PointerValue {  // 클래스 선언: 좌표값 처리를 위한 메인 클래스
//    public static void main(String[] args) {  // 프로그램의 시작점
//        Scanner sc = new Scanner(System.in);  // 사용자 입력을 받기 위한 Scanner 객체 생성
//        try {  // 예외 처리 시작: 입력값 처리 중 발생할 수 있는 예외 처리를 위한 블록
//            System.out.println("내 좌표 x값을 입력해 주세요.");  // 사용자에게 x좌표 입력 요청
//            int myX = sc.nextInt();  // x좌표 정수값 입력 받음 (잘못된 입력 시 InputMismatchException 발생 가능)
//            System.out.println("내 좌표 y값을 입력해 주세요.");  // 사용자에게 y좌표 입력 요청
//            int myY = sc.nextInt();  // y좌표 정수값 입력 받음
//            Point myPoint = new Point(myX, myY);  // 입력받은 x,y 좌표로 Point 객체 생성
//
//            int targetCount = 10;  // 입력받을 총 좌표 개수를 상수로 설정
//            System.out.println("1/" + targetCount + " 번째 입력");  // 첫 번째 좌표 입력 안내
//            Set<Point> pointerSet = inputPointer(sc, targetCount);  // 사용자로부터 좌표들을 입력받아 Set에 저장
//
//            StringBuilder outputBuilder = new StringBuilder();  // 결과 문자열을 효율적으로 구성하기 위한 StringBuilder 객체 생성
//            Point nearestPoint = findNearestPointer(myPoint, pointerSet, outputBuilder);  // 가장 가까운 좌표 찾기
//
//            System.out.print(outputBuilder.toString());  // 모든 좌표와의 거리 계산 결과 출력
//            System.out.println("제일 가까운 좌표:");  // 가장 가까운 좌표 안내 메시지 출력
//            System.out.printf("(%d, %d) => %.6f%n",  // 가장 가까운 좌표와 그 거리를 소수점 6자리까지 출력
//                nearestPoint.x, nearestPoint.y,
//                Math.sqrt(distanceSquared(myPoint, nearestPoint)));
//
//        } catch (Exception e) {  // 모든 예외 상황 처리: InputMismatchException, SecurityException 등
//            System.out.println("오류 발생: " + e.getMessage());  // 예외 메시지 출력으로 사용자에게 오류 상황 전달
//        } finally {  // 예외 발생 여부와 관계없이 실행되는 블록
//            sc.close();  // Scanner 리소스 해제: 메모리 누수 방지
//        }
//    }
//
//    private static Set<Point> inputPointer(Scanner sc, int targetCount) {  // 좌표들을 입력받는 메소드
//        Set<Point> pointerSet = new LinkedHashSet<>();  // 중복 없는 좌표 저장을 위한 LinkedHashSet 생성 (입력 순서 유지)
//
//        while (pointerSet.size() < targetCount) {  // 목표 개수만큼 좌표가 입력될 때까지 반복
//            try {  // 좌표 입력 과정에서 발생할 수 있는 예외 처리
//                System.out.println("임의의 좌표 x값을 입력해 주세요.");  // x좌표 입력 안내
//                int x = sc.nextInt();  // x좌표 입력 받기
//                System.out.println("임의의 좌표 y값을 입력해 주세요.");  // y좌표 입력 안내
//                int y = sc.nextInt();  // y좌표 입력 받기
//
//                Point point = new Point(x, y);  // 입력받은 좌표로 Point 객체 생성
//
//                if (!pointerSet.add(point)) {  // Set에 좌표 추가 시도. 추가 실패(중복)시 false 반환
//                    System.out.println("동일한 좌표값이 이미 존재합니다. 다시 입력해 주세요.");  // 중복 좌표 입력 시 안내 메시지
//                    continue;  // 현재 반복 건너뛰고 다음 반복으로
//                }
//
//                if (pointerSet.size() < targetCount) {  // 아직 입력해야 할 좌표가 남았다면
//                    System.out.println((pointerSet.size() + 1) + "/" + targetCount + " 번째 입력");  // 다음 입력 순서 안내
//                }
//
//            } catch (Exception e) {  // 입력 과정의 예외 처리: InputMismatchException 등
//                System.out.println("오류 발생: " + e.getMessage());  // 예외 메시지 출력
//                sc.next();  // 입력 버퍼 비우기: 잘못된 입력 제거
//            }
//        }
//
//        return pointerSet;  // 입력받은 모든 좌표가 담긴 Set 반환
//    }
//
//    private static Point findNearestPointer(Point myPoint, Set<Point> pointerSet, StringBuilder outputBuilder) {  // 가장 가까운 좌표 찾는 메소드
//        Point nearestPointer = null;  // 가장 가까운 좌표를 저장할 변수 초기화
//        double minDistance = Double.MAX_VALUE;  // 최소 거리를 저장할 변수를 최대값으로 초기화
//
//        for (Point point : pointerSet) {  // 모든 좌표에 대해 반복
//            double distance = distanceSquared(myPoint, point);  // 현재 좌표와의 거리 계산
//            outputBuilder.append(String.format("(%d, %d) => %.6f%n",  // 현재 좌표와의 거리를 문자열로 변환하여 저장
//                point.x, point.y, Math.sqrt(distance)));
//
//            if (distance < minDistance) {  // 현재까지의 최소 거리보다 더 가까운 경우
//                minDistance = distance;  // 최소 거리 업데이트
//                nearestPointer = point;  // 가장 가까운 좌표 업데이트
//            }
//        }
//
//        return nearestPointer;  // 가장 가까운 좌표 반환
//    }
//
//    private static double distanceSquared(Point p1, Point p2) {  // 두 점 사이의 거리 제곱을 계산하는 메소드
//        // 유클리드 거리의 제곱을 계산: (x2-x1)^2 + (y2-y1)^2
//        // Math.sqrt 연산을 최소화하여 성능 향상
//        return Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2);  // 피타고라스 정리를 이용한 거리 계산
//    }
//}