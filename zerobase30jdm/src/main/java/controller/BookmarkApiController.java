package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Bookmark;
import service.BookmarkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookmark/add")
public class BookmarkApiController extends HttpServlet {
    private final BookmarkService bookmarkService = new BookmarkService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bookmark bookmark = objectMapper.readValue(req.getReader(), Bookmark.class);
            bookmarkService.insertBookmark(bookmark);
            resp.getWriter().write("{\"message\": \"Bookmark added successfully\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to add bookmark\"}");
        }
    }
}