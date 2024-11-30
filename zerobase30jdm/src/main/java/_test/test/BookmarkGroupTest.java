package _test.test;

import controller.BookmarkGroupController;

public class BookmarkGroupTest {
    public static void main(String[] args) {
        BookmarkGroupController controller = new BookmarkGroupController();

        // Test 1: addBookmarkGroup - 입력값이 비어 있는 경우
        try {
            controller.addBookmarkGroup("", 1);
            System.out.println("Test 1 실패: 빈 이름으로 그룹 추가가 성공했음.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 통과: " + e.getMessage());
        }

        // Test 2: addBookmarkGroup - 중복된 순서
        try {
            controller.addBookmarkGroup("Group1", 1);
            controller.addBookmarkGroup("Group2", 1); // 동일한 순서로 추가 시도
            System.out.println("Test 2 실패: 중복된 순서로 그룹 추가가 성공했음.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 통과: " + e.getMessage());
        }

        // Test 3: deleteBookmarkGroup - 존재하지 않는 ID
        try {
            controller.deleteBookmarkGroup(-1); // 잘못된 ID
            System.out.println("Test 3 실패: 잘못된 ID로 그룹 삭제가 성공했음.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 3 통과: " + e.getMessage());
        }

        // Test 4: getMaxOrderNo - 데이터베이스가 비어 있는 경우
        try {
            int maxOrderNo = controller.getMaxOrderNo();
            System.out.println("Test 4 통과: 최대 순서 값은 " + maxOrderNo);
        } catch (RuntimeException e) {
            System.out.println("Test 4 실패: " + e.getMessage());
        }

        // Test 5: validateGroupInput - 너무 긴 이름
        try {
            controller.addBookmarkGroup("ThisNameIsWayTooLongAndShouldFailBecauseItExceedsThe255CharacterLimit...", 1);
            System.out.println("Test 5 실패: 너무 긴 이름으로 그룹 추가가 성공했음.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 통과: " + e.getMessage());
        }
    }
}