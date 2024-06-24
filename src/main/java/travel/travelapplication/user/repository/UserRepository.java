package travel.travelapplication.user.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import travel.travelapplication.user.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByRefreshToken(String refreshToken);
}
