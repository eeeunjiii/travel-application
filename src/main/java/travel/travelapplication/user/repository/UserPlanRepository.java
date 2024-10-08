package travel.travelapplication.user.repository;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;

import travel.travelapplication.user.domain.UserPlan;

public interface UserPlanRepository extends MongoRepository<UserPlan, ObjectId> {

}
