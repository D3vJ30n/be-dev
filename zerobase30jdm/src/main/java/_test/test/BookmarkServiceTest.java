package _test.test;

import model.Bookmark;
import service.BookmarkService;

import java.util.List;

public class BookmarkServiceTest {
    public static void main(String[] args) {
        BookmarkService bookmarkService = new BookmarkService();

        // 1. 즐겨찾기 추가 테스트
        try {
            Bookmark bookmark = new Bookmark();
            bookmark.setGroupId(1); // 유효한 그룹 ID로 변경
            bookmark.setWifiMgrNo("WIFI001"); // 유효한 와이파이 관리 번호로 변경
            bookmarkService.insertBookmark(bookmark);
            System.out.println("즐겨찾기 추가 완료: " + bookmark);
        } catch (Exception e) {
            System.err.println("즐겨찾기 추가 중 오류 발생: " + e.getMessage());
        }

        // 2. 즐겨찾기 목록 조회 테스트
        try {
            List<Bookmark> bookmarkList = bookmarkService.getBookmarkList();
            System.out.println("조회된 즐겨찾기 목록:");
            bookmarkList.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("즐겨찾기 목록 조회 중 오류 발생: " + e.getMessage());
        }

        // 3. 특정 즐겨찾기 조회 테스트
        try {
            int bookmarkId = 1; // 존재하는 즐겨찾기 ID로 설정
            Bookmark bookmark = bookmarkService.getBookmark(bookmarkId);
            System.out.println("조회된 즐겨찾기: " + bookmark);
        } catch (Exception e) {
            System.err.println("특정 즐겨찾기 조회 중 오류 발생: " + e.getMessage());
        }

        // 4. 특정 그룹의 즐겨찾기 목록 조회 테스트
        try {
            int groupId = 1; // 존재하는 그룹 ID로 설정
            List<Bookmark> groupBookmarkList = bookmarkService.getBookmarkListByGroupId(groupId);
            System.out.println("그룹별 즐겨찾기 목록:");
            groupBookmarkList.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("그룹별 즐겨찾기 목록 조회 중 오류 발생: " + e.getMessage());
        }

        // 5. 즐겨찾기 삭제 테스트
        try {
            int deleteId = 1; // 삭제할 즐겨찾기 ID로 설정
            bookmarkService.deleteBookmark(deleteId);
            System.out.println("즐겨찾기 삭제 완료: ID " + deleteId);
        } catch (Exception e) {
            System.err.println("즐겨찾기 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
