package controller;

import model.Bookmark;
import model.BookmarkGroup;
import service.BookmarkService;
import service.BookmarkGroupService;
import java.util.List;

public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkGroupService bookmarkGroupService;

    public BookmarkController() {
        this.bookmarkService = new BookmarkService();
        this.bookmarkGroupService = new BookmarkGroupService();
    }

    // Bookmark 목록 조회
    public List<Bookmark> getBookmarkList() {
        return bookmarkService.getBookmarkList();
    }

    // BookmarkGroup 목록 조회
    public List<BookmarkGroup> getBookmarkGroupList() {
        return bookmarkGroupService.getBookmarkGroupList();
    }

    // Bookmark 추가
    public void addBookmark(int groupId, String wifiId, String wifiName) {
        try {
            Bookmark bookmark = new Bookmark();
            bookmark.setGroupId(groupId);
            bookmark.setWifiId(wifiId);
            bookmark.setWifiName(wifiName);
            bookmarkService.insertBookmark(bookmark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Bookmark 삭제
    public void deleteBookmark(int id) {
        try {
            bookmarkService.deleteBookmark(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 특정 Bookmark 조회
    public Bookmark getBookmark(int id) {
        return bookmarkService.getBookmark(id);
    }

    // 특정 BookmarkGroup 조회
    public BookmarkGroup getBookmarkGroup(int id) {
        return bookmarkGroupService.getBookmarkGroup(id);
    }
}
