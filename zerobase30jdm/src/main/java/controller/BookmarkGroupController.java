package controller;

import model.BookmarkGroup;
import service.BookmarkGroupService;
import java.util.List;

/**
 * BookmarkGroupController 클래스
 * 북마크 그룹과 관련된 로직을 처리하고 서비스 레이어와 상호작용하는 컨트롤러
 * 사용자 요청을 처리하고 서비스 메서드를 호출하여 적절한 응답을 반환
 */
public class BookmarkGroupController {
    private final BookmarkGroupService bookmarkGroupService;

    /**
     * 기본 생성자
     * BookmarkGroupService 인스턴스를 초기화
     */
    public BookmarkGroupController() {
        this.bookmarkGroupService = new BookmarkGroupService();
    }

    /**
     * 북마크 그룹 목록 조회
     * @return 북마크 그룹 목록
     */
    public List<BookmarkGroup> getBookmarkGroupList() {
        try {
            return bookmarkGroupService.getBookmarkGroupList();
        } catch (RuntimeException e) {
            System.err.println("즐겨찾기 그룹 목록 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 즐겨찾기 그룹 추가
     * @param name 그룹 이름
     * @param orderNo 그룹 순서
     */
    public void addBookmarkGroup(String name, int orderNo) {
        try {
            // 입력값 검증
            validateGroupInput(name, orderNo, null);

            // 순서 중복 확인
            if (bookmarkGroupService.isOrderNoExists(orderNo, null)) {
                throw new IllegalArgumentException("이미 사용 중인 순서입니다: " + orderNo);
            }

            BookmarkGroup group = new BookmarkGroup();
            group.setName(name);
            group.setOrderNo(orderNo);

            bookmarkGroupService.insertBookmarkGroup(group);
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 입력값: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("즐겨찾기 그룹 추가 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 즐겨찾기 그룹 수정
     * @param id 그룹 ID
     * @param name 그룹 이름
     * @param orderNo 그룹 순서
     */
    public void updateBookmarkGroup(int id, String name, int orderNo) {
        try {
            // 입력값 검증
            validateGroupInput(name, orderNo, id);

            // 순서 중복 확인
            if (bookmarkGroupService.isOrderNoExists(orderNo, id)) {
                throw new IllegalArgumentException("이미 사용 중인 순서입니다: " + orderNo);
            }

            BookmarkGroup group = new BookmarkGroup();
            group.setId(id);
            group.setName(name);
            group.setOrderNo(orderNo);

            bookmarkGroupService.updateBookmarkGroup(group);
        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 입력값: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            System.err.println("즐겨찾기 그룹 수정 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 즐겨찾기 그룹 삭제
     * @param id 그룹 ID
     */
    public void deleteBookmarkGroup(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }

            // 삭제 전 존재 여부 확인
            BookmarkGroup group = bookmarkGroupService.getBookmarkGroup(id);
            if (group == null) {
                throw new IllegalArgumentException("존재하지 않는 즐겨찾기 그룹입니다.");
            }

            bookmarkGroupService.deleteBookmarkGroup(id);
        } catch (RuntimeException e) {
            System.err.println("즐겨찾기 그룹 삭제 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 즐겨찾기 그룹 상세 조회
     * @param id 그룹 ID
     * @return 조회된 즐겨찾기 그룹
     */
    public BookmarkGroup getBookmarkGroup(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
            }
            return bookmarkGroupService.getBookmarkGroup(id);
        } catch (RuntimeException e) {
            System.err.println("즐겨찾기 그룹 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 입력값 검증 메서드
     * @param name 그룹 이름
     * @param orderNo 그룹 순서
     * @param excludeId 제외할 그룹 ID (중복 검사를 위한 용도)
     */
    private void validateGroupInput(String name, int orderNo, Integer excludeId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("그룹 이름은 필수 값입니다.");
        }
        if (name.trim().length() > 255) { // ERD varchar(255) 제약조건 반영
            throw new IllegalArgumentException("그룹 이름이 너무 깁니다.");
        }
        if (orderNo <= 0) {
            throw new IllegalArgumentException("순서는 0보다 커야 합니다.");
        }
    }

    /**
     * 그룹 존재 여부 확인
     * @param id 그룹 ID
     * @return 존재 여부 (true/false)
     */
    public boolean existsBookmarkGroup(int id) {
        try {
            if (id <= 0) {
                return false;
            }
            return bookmarkGroupService.getBookmarkGroup(id) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 순서 중복 체크
     * @param orderNo 그룹 순서
     * @param excludeId 제외할 그룹 ID
     * @return 중복 여부 (true/false)
     */
    public boolean isOrderNoExists(int orderNo, Integer excludeId) {
        try {
            return bookmarkGroupService.isOrderNoExists(orderNo, excludeId);
        } catch (RuntimeException e) {
            System.err.println("순서 중복 체크 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    /**
     * 최대 순서 값 조회
     * @return 최대 순서 값
     */
    public int getMaxOrderNo() {
        try {
            List<BookmarkGroup> groups = getBookmarkGroupList();
            return groups.stream()
                .mapToInt(BookmarkGroup::getOrderNo)
                .max()
                .orElse(0);
        } catch (RuntimeException e) {
            System.err.println("최대 순서 값 조회 중 오류 발생: " + e.getMessage());
            return 0;
        }
    }
}
