package travel.travelapplication.plan.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.plan.domain.Reply;

public interface ReplyRepository extends MongoRepository<Reply, ObjectId> {
}
