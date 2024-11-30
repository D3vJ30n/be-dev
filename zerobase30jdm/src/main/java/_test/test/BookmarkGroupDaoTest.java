package _test.test;

import dao.BookmarkGroupDao;
import model.BookmarkGroup;

import java.util.List;

public class BookmarkGroupDaoTest {
    public static void main(String[] args) {
        BookmarkGroupDao dao = new BookmarkGroupDao();

        // 1. 추가 테스트
        BookmarkGroup group1 = new BookmarkGroup("Group1", 1);
        dao.insertBookmarkGroup(group1);
        System.out.println("추가 완료: " + group1);

        // 2. 조회 테스트
        List<BookmarkGroup> groupList = dao.getBookmarkGroupList();
        System.out.println("조회된 그룹 목록: ");
        for (BookmarkGroup group : groupList) {
            System.out.println(group);
        }

        // 3. 수정 테스트
        if (!groupList.isEmpty()) {
            BookmarkGroup groupToUpdate = groupList.get(0);
            groupToUpdate.setName("Updated Group");
            groupToUpdate.setOrderNo(2);
            dao.updateBookmarkGroup(groupToUpdate);
            System.out.println("수정 완료: " + groupToUpdate);
        }

        // 4. 삭제 테스트
        if (!groupList.isEmpty()) {
            int groupIdToDelete = groupList.get(0).getId();
            dao.deleteBookmarkGroup(groupIdToDelete);
            System.out.println("삭제 완료: ID " + groupIdToDelete);
        }

        // 5. 최종 목록 확인
        groupList = dao.getBookmarkGroupList();
        System.out.println("최종 그룹 목록: " + groupList);
    }
}
