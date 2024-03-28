package travel.travelapplication.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.entity.Plan;

public interface PlanRepository extends MongoRepository<Plan, ObjectId> {


}
