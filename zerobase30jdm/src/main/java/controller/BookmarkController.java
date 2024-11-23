package controller;

import model.Bookmark;
import service.BookmarkService;
import java.util.List;

/**
 * BookmarkController 클래스
 * 북마크와 관련된 로직을 처리하고 서비스 레이어와 상호작용하는 컨트롤러
 * 주로 사용자 요청을 처리하고 적절한 서비스 메서드를 호출하여 응답을 반환
 */
public class BookmarkController {
    private final BookmarkService bookmarkService;

    /**
     * 기본 생성자
     * BookmarkService 인스턴스를 초기화
     */
    public BookmarkController() {
        this.bookmarkService = new BookmarkService();
    }

    /**
     * 북마크 목록 조회
     * @return 북마크 목록
     */
    public List<Bookmark> getBookmarkList() {
        try {
            return bookmarkService.getBookmarkList();
        } catch (RuntimeException e) {
            System.err.println("북마크 목록 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 북마크 추가
     * @param groupId 그룹 ID
     * @param wifiMgrNo 와이파이 관리번호
     * @param wifiName 와이파이 이름
     */
    public void addBookmark(int groupId, String wifiMgrNo, String wifiName) {
        try {
            // 입력값 검증
            validateInput(groupId, wifiMgrNo);

            Bookmark bookmark = new Bookmark();
            bookmark.setGroupId(groupId);
            bookmark.setWifiMgrNo(wifiMgrNo);
            bookmark.setWifiName(wifiName);

            bookmarkService.insertBookmark(bookmark);
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 입력값: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("북마크 추가 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 북마크 삭제
     * @param id 북마크 ID
     */
    public void deleteBookmark(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            bookmarkService.deleteBookmark(id);
        } catch (RuntimeException e) {
            System.err.println("북마크 삭제 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 단일 북마크 조회
     * @param id 북마크 ID
     * @return 조회된 북마크
     */
    public Bookmark getBookmark(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            return bookmarkService.getBookmark(id);
        } catch (RuntimeException e) {
            System.err.println("북마크 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 그룹별 북마크 목록 조회
     * @param groupId 그룹 ID
     * @return 그룹에 속한 북마크 목록
     */
    public List<Bookmark> getBookmarkListByGroupId(int groupId) {
        try {
            if (groupId <= 0) {
                throw new IllegalArgumentException("유효하지 않은 그룹 ID 값입니다.");
            }
            return bookmarkService.getBookmarkListByGroupId(groupId);
        } catch (RuntimeException e) {
            System.err.println("그룹별 북마크 목록 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 입력값 검증 메서드
     * @param groupId 그룹 ID
     * @param wifiMgrNo 와이파이 관리번호
     */
    private void validateInput(int groupId, String wifiMgrNo) {
        if (groupId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 그룹 ID 값입니다.");
        }
        if (wifiMgrNo == null || wifiMgrNo.trim().isEmpty()) {
            throw new IllegalArgumentException("와이파이 관리번호는 필수 값입니다.");
        }
        if (wifiMgrNo.length() > 50) {  // ERD의 varchar(50) 제약조건 반영
            throw new IllegalArgumentException("와이파이 관리번호가 너무 깁니다.");
        }
    }

    /**
     * 북마크 존재 여부 확인
     * @param id 북마크 ID
     * @return 존재 여부 (true/false)
     */
    public boolean existsBookmark(int id) {
        try {
            return getBookmark(id) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 특정 그룹의 북마크 개수 조회
     * @param groupId 그룹 ID
     * @return 해당 그룹의 북마크 개수
     */
    public int getBookmarkCountByGroupId(int groupId) {
        try {
            return getBookmarkListByGroupId(groupId).size();
        } catch (RuntimeException e) {
            System.err.println("그룹별 북마크 개수 조회 중 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    /**
     * 중복 북마크 체크
     * 같은 그룹에 동일한 와이파이가 이미 북마크되어 있는지 확인
     * @param groupId 그룹 ID
     * @param wifiMgrNo 와이파이 관리번호
     * @return 중복 여부 (true/false)
     */
    public boolean isDuplicateBookmark(int groupId, String wifiMgrNo) {
        try {
            List<Bookmark> groupBookmarks = getBookmarkListByGroupId(groupId);
            return groupBookmarks.stream()
                .anyMatch(b -> b.getWifiMgrNo().equals(wifiMgrNo));
        } catch (RuntimeException e) {
            System.err.println("북마크 중복 체크 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
