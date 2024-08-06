package travel.travelapplication.place.repository;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.place.domain.Place;

public interface PlaceRepository extends MongoRepository<Place, Long> {
    Optional<Place> findByName(String name);
}
