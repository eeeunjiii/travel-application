package travel.travelapplication.place.repository;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import travel.travelapplication.place.domain.Place;

public interface PlaceRepository extends MongoRepository<Place, ObjectId> {
    Optional<Place> findByName(String name);

    Optional<Place> findByPlaceId(String placeId);

    @Query("{ '_id': ?0 }")
    Optional<Place> findByStringifiedId(String id);
}
