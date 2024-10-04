package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ProvCity")
@Getter
@NoArgsConstructor
public class ProvCity {

    @Id
    private ObjectId id;

    private String name;

    private List<String> districts=new ArrayList<>();

    public ProvCity(String name) {
        this.name = name;
    }
}
