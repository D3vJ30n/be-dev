<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
<%@ page import="controller.BookmarkGroupController"%>
<%@ page import="model.BookmarkGroup"%>

<%
    request.setCharacterEncoding("UTF-8");

    // 입력값 처리
    String name = request.getParameter("name");
    String orderNoParam = request.getParameter("order");

    int orderNo;
    BookmarkGroupController controller = new BookmarkGroupController();

    try {
        // 순서 값 확인
        if (orderNoParam == null || orderNoParam.trim().isEmpty()) {
            // 순서 값이 비어 있으면 최대 순서 값 + 1 할당
            orderNo = controller.getMaxOrderNo() + 1;
        } else {
            // 입력된 순서 값 처리
            orderNo = Integer.parseInt(orderNoParam);
        }

        // 즐겨찾기 그룹 추가
        controller.addBookmarkGroup(name, orderNo);

        // 성공 메시지와 함께 목록으로 리다이렉트
        out.println("<script>alert('즐겨찾기 그룹이 성공적으로 추가되었습니다.'); location.href='/bookmark-group.jsp';</script>");
    } catch (IllegalArgumentException e) {
        // 유효성 검사 실패 시 메시지 출력
        out.println("<script>alert('입력 오류: " + e.getMessage() + "'); history.back();</script>");
    } catch (Exception e) {
        // 기타 예외 처리
        e.printStackTrace();
        out.println("<script>alert('즐겨찾기 그룹 추가 중 오류 발생: " + e.getMessage() + "'); history.back();</script>");
    }
%>
