package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import travel.travelapplication.constant.TransportationType;

import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    @Builder
    public Transportation(String name, TransportationType type) {
        this.name = name;
        this.type = type;
    }
}
