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
}
