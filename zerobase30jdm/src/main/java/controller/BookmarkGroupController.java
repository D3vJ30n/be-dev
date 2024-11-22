package controller;

import model.BookmarkGroup;
import service.BookmarkGroupService;
import java.util.List;

public class BookmarkGroupController {
    private final BookmarkGroupService bookmarkGroupService;

    public BookmarkGroupController() {
        this.bookmarkGroupService = new BookmarkGroupService();
    }

    public List<BookmarkGroup> getBookmarkGroupList() {
        return bookmarkGroupService.getBookmarkGroupList();
    }

    public void addBookmarkGroup(String name, int order) {
        try {
            BookmarkGroup group = new BookmarkGroup();
            group.setName(name);
            group.setOrder(order);
            bookmarkGroupService.insertBookmarkGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBookmarkGroup(int id, String name, int order) {
        try {
            BookmarkGroup group = new BookmarkGroup();
            group.setId(id);
            group.setName(name);
            group.setOrder(order);
            bookmarkGroupService.updateBookmarkGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBookmarkGroup(int id) {
        try {
            bookmarkGroupService.deleteBookmarkGroup(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BookmarkGroup getBookmarkGroup(int id) {
        return bookmarkGroupService.getBookmarkGroup(id);
    }
}