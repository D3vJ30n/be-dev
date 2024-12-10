//package study.backend.studying.src.zbmissions;
//
//import java.awt.*;
//import java.util.LinkedHashSet;
//import java.util.Scanner;
//import java.util.Set;
//
//// 두 좌표 간의 최단 거리를 찾는 프로그램
//public class NearestLocationFinder {
//    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        try {
//            // 내 위치 입력받기
//            System.out.println("내 좌표를 입력하세요.");
//            Point myLocation = getLocation(input);
//
//            // 좌표 개수 물어보기
//            System.out.println("몇 개의 임의의 좌표를 입력하시겠습니까?");
//            int targetCount = input.nextInt();
//            if (targetCount <= 0) {
//                throw new IllegalArgumentException("1개 이상 입력해야 합니다!");
//            }
//
//            // 좌표들 입력받아서 저장하기 (중복 체크)
//            Set<Point> locations = new LinkedHashSet<>();
//            System.out.println("\n자, " + targetCount + "개의 좌표를 입력해주세요.");
//
//            while (locations.size() < targetCount) {
//                Point newLocation = getLocation(input);
//
//                // 중복된 좌표는 다시 입력받기
//                if (!locations.add(newLocation)) {
//                    System.out.println("이미 있는 좌표입니다. 다른 걸 입력하세요.");
//                    continue;
//                }
//
//                // 입력 진행상황 보여주기
//                System.out.println(locations.size() + "개 입력완료! " +
//                    (targetCount - locations.size() == 0 ? "끝!" : (targetCount - locations.size()) + "개 남았습니다."));
//            }
//
//            // 제일 가까운 좌표 찾고 결과 보여주기
//            Point nearestLocation = findNearestLocation(myLocation, locations);
//
//            System.out.println("\n=== 찾았다! ===");
//            System.out.println("내 위치: (" + myLocation.x + ", " + myLocation.y + ")");
//            System.out.println("가장 가까운 좌표: (" + nearestLocation.x + ", " + nearestLocation.y + ")");
//            System.out.printf("거리: %.2f units", myLocation.distance(nearestLocation));
//
//        } catch (Exception e) {
//            System.out.println("에러 발생! " + e.getMessage());
//        } finally {
//            input.close();
//        }
//    }
//
//    // x, y 좌표 입력받아서 Point 만들기
//    private static Point getLocation(Scanner input) {
//        System.out.print("X 좌표: ");
//        int xCoordinate = input.nextInt();
//        System.out.print("Y 좌표: ");
//        int yCoordinate = input.nextInt();
//        return new Point(xCoordinate, yCoordinate);
//    }
//
//    // 내 위치에서 가장 가까운 좌표 찾기
//    private static Point findNearestLocation(Point myLocation, Set<Point> locations) {
//        Point nearestLocation = null;
//        double shortestDistance = Double.MAX_VALUE;  // 처음엔 최대값으로 설정
//
//        // 모든 좌표 돌면서 거리 비교
//        for (Point location : locations) {
//            double currentDistance = myLocation.distance(location);
//            if (currentDistance < shortestDistance) {
//                shortestDistance = currentDistance;
//                nearestLocation = location;
//            }
//        }
//        return nearestLocation;
//    }
//}
