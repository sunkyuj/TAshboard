package project.tashboard.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Board {
    private BoardType boardType;
    private String name;
    private String description;
    private String url; // {"ta", "school"}
}
