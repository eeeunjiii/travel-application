package travel.travelapplication.plan.repository;

import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import travel.travelapplication.plan.domain.Plan;

@Repository
public interface PlanRepository extends MongoRepository<Plan, Long> {

}
