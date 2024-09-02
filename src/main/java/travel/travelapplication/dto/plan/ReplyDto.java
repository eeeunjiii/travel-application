package travel.travelapplication.dto.plan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDto {
    private String content;
    private String email;

    public ReplyDto(String content, String email) {
        this.content=content;
        this.email=email;
    }
}
