package project.tashboard.domain.board;

import java.util.List;

public class BoardLists {
    public static final List<Board> boardList = List.of(
            new Board(BoardType.TA, "교생 게시판", "교생 게시판입니다.", "ta"),
            new Board(BoardType.SCHOOL, "학교 게시판", "학교 게시판입니다.", "school")
    );
}
