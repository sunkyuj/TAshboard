package project.tashboard.domain.board;

public enum BoardType {
    TA, SCHOOL;

    public String getBoardPath() {
        return switch (this) {
            case TA -> "ta";
            case SCHOOL -> "school";
            default -> "";
        };
    }
    public String getBoardName() {
        return switch (this) {
            case TA -> "교생 게시판";
            case SCHOOL -> "학교 게시판";
            default -> "";
        };
    }
}
