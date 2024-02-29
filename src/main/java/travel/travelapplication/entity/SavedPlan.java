package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class SavedPlan {

    @Id
    private Long id;

    @DBRef
    private Plan plan;

    public SavedPlan(Plan plan) {
        this.plan = plan;
    }
}
