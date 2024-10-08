package travel.travelapplication.place.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.place.domain.ProvCity;

import java.util.Optional;

public interface ProvCityRepository extends MongoRepository<ProvCity, ObjectId> {
    Optional<ProvCity> findByName(String name);
}
