package travel.travelapplication.dto.plan;

import lombok.Getter;
import lombok.Setter;
import travel.travelapplication.plan.domain.Comment;

import java.util.LinkedList;

@Getter
@Setter
public class CommentDto {

    private String content;
    private String email;

    public CommentDto() {

    }

    public CommentDto(String content, String email) {
        this.content = content;
        this.email = email;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .email(email)
                .replies(new LinkedList<>())
                .build();

    }
}
