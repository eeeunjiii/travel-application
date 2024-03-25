package travel.travelapplication.user.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import travel.travelapplication.user.domain.SavedPlan;

@Repository
public interface SavedPlanRepository extends MongoRepository<SavedPlan, ObjectId> {
}
