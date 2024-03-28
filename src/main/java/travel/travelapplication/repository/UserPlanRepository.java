package travel.travelapplication.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.entity.UserPlan;

public interface UserPlanRepository extends MongoRepository<UserPlan, ObjectId> {

//    List<UserPlan> findByPlace(String placeName);
}
