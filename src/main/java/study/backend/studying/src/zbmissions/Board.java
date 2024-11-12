package study.backend.studying.src.zbmissions;

public class Board {
    /**
     * 페이지네이션 구현에 필요한 인스턴스 변수들
     * totalCount: 전체 게시글의 총 개수 (예: 127개)
     * postsPerPage: 한 페이지에 표시할 게시글의 수 (기본값: 10)
     * pageBlockCount: 하단에 표시할 페이지 번호의 개수 (기본값: 10) - 예: [1,2,3,4,5,6,7,8,9,10]
     * pageIndex: 사용자가 현재 보고 있는 페이지 번호
     */
    private long totalCount;
    private int postsPerPage = 10;
    private int pageBlockCount = 10;
    private long pageIndex;

    /**
     * Board 클래스의 생성자
     * @param totalCount 페이지네이션 처리할 전체 게시글의 수를 받아서 초기화
     */
    public Board(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 전체 페이지 수를 계산하는 메서드
     * 예: 총 게시글이 127개이고 페이지당 10개씩 표시한다면
     * 127/10 = 12.7을 올림처리하여 13페이지가 필요
     * @return 올림처리된 전체 페이지 수
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) totalCount / postsPerPage);
    }

    /**
     * 현재 페이지가 속한 페이지 그룹 번호를 계산
     * 예: pageBlockCount가 10일 때
     * 1~10페이지는 1그룹
     * 11~20페이지는 2그룹
     * @return 현재 페이지가 속한 그룹 번호
     */
    public int getCurrentPageGroup() {
        return (int) Math.ceil((double) pageIndex / pageBlockCount);
    }

    /**
     * 현재 페이지 그룹의 시작 페이지 번호를 계산
     * 예: 2그룹일 경우 (2-1)*10 + 1 = 11이 시작 페이지
     * @return 현재 그룹의 시작 페이지 번호
     */
    public int getStartPage() {
        return (getCurrentPageGroup() - 1) * pageBlockCount + 1;
    }

    /**
     * 현재 페이지 그룹의 마지막 페이지 번호를 계산
     * 예: 2그룹의 경우 2*10 = 20이 마지막이지만,
     * 전체 페이지가 13페이지라면 13이 마지막 페이지가 됨
     * @return 현재 그룹의 마지막 페이지 번호
     */
    public int getEndPage() {
        return Math.min(getCurrentPageGroup() * pageBlockCount, getTotalPages());
    }

    /**
     * 이전 페이지 그룹 존재 여부 확인
     * 현재 그룹이 1보다 크면 이전 그룹이 존재
     * @return 이전 그룹 존재 여부 (true/false)
     */
    public boolean isPrevPage() {
        return getCurrentPageGroup() > 1;
    }

    /**
     * 다음 페이지 그룹 존재 여부 확인
     * 현재 그룹이 마지막 그룹보다 작으면 다음 그룹이 존재
     * @return 다음 그룹 존재 여부 (true/false)
     */
    public boolean isNextPage() {
        return getCurrentPageGroup() < (int) Math.ceil((double) getTotalPages() / pageBlockCount);
    }

    /**
     * 이전 블록([이전] 버튼) 표시 여부 확인
     * 시작 페이지가 1보다 크면 이전 블록이 존재
     * @return 이전 블록 표시 여부 (true/false)
     */
    public boolean isPrevBlock() {
        return getStartPage() > 1;
    }

    /**
     * 다음 블록([다음] 버튼) 표시 여부 확인
     * 현재 그룹의 마지막 페이지가 전체 페이지 수보다 작으면 다음 블록이 존재
     * @return 다음 블록 표시 여부 (true/false)
     */
    public boolean isNextBlock() {
        return getEndPage() < getTotalPages();
    }

    /**
     * 페이지네이션 HTML을 생성하는 메서드
     * @param pageIndex 현재 선택된 페이지 번호
     * @return 생성된 페이지네이션 HTML 문자열
     */
    public String html(long pageIndex) {
        // 현재 페이지 번호를 인스턴스 변수에 저장
        this.pageIndex = pageIndex;

        // HTML을 만들기 위한 StringBuilder 객체 생성
        StringBuilder sb = new StringBuilder();

        // 처음과 이전 버튼 추가
        sb.append("<a href='#'>[처음]</a>\n");  // [처음] 버튼 생성
        sb.append("<a href='#'>[이전]</a>\n\n");  // [이전] 버튼 생성 후 줄바꿈으로 간격 추가

        // 페이지 번호 버튼들 생성
        for (int i = getStartPage(); i <= getEndPage(); i++) {
            if (i == pageIndex) {
                // 현재 페이지일 경우 'on' 클래스 추가
                sb.append("<a href='#' class='on'>" + i + "</a>\n");
            } else {
                // 현재 페이지가 아닌 경우 일반 링크로 생성
                sb.append("<a href='#'>" + i + "</a>\n");
            }
        }

        // 페이지 번호와 다음 버튼 사이에 간격을 위한 줄바꿈
        sb.append("\n");

        // 다음과 마지막 버튼 추가
        sb.append("<a href='#'>[다음]</a>\n");  // [다음] 버튼 생성
        sb.append("<a href='#'>[마지막]</a>");  // [마지막] 버튼 생성

        // 완성된 HTML 문자열 반환
        return sb.toString();
    }

    /**
     * 페이지네이션 동작을 테스트하는 메인 메서드
     * @param args 명령행 인자 (사용하지 않음)
     */
    public static void main(String[] args) {
        // 전체 게시글 수 127개, 현재 페이지 1페이지로 테스트
        long totalCount = 127;  // 전체 게시글 수 설정
        long pageIndex = 1;     // 현재 페이지 번호 설정

        // Board 객체 생성 및 페이지네이션 HTML 출력
        Board pager = new Board(totalCount);
        System.out.println(pager.html(pageIndex));
    }
}