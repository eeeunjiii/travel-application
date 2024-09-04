package travel.travelapplication.plan.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.plan.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
}
