package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.BookmarkGroup;
import service.BookmarkGroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookmark-group/list")
public class BookmarkGroupApiController extends HttpServlet {
    private final BookmarkGroupService bookmarkGroupService = new BookmarkGroupService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            List<BookmarkGroup> groups = bookmarkGroupService.getBookmarkGroupList();
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(groups));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to fetch bookmark groups\"}");
        }
    }
}
