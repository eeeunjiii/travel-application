package travel.travelapplication.user.repository;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import travel.travelapplication.user.domain.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);
}
