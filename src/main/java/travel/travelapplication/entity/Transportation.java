package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.TransportationType;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class Transportation {

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    public Transportation(String name, TransportationType type) {
        this.name = name;
        this.type = type;
    }
}
