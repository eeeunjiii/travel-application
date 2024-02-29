package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class Tag {

    @Id
    private Long id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
