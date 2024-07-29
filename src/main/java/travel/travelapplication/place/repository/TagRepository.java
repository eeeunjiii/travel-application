package travel.travelapplication.place.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.place.domain.Tag;

public interface TagRepository extends MongoRepository<Tag, ObjectId> {
}
