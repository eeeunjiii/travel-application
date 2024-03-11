package travel.travelapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.entity.Tag;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void addTag(String email, Tag tag) throws IllegalAccessException {
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isPresent()) {
//            user.addTag(tag);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public List<Tag> findAllTag(String email) throws IllegalAccessException {
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isPresent()) {
//            return user.getTags();
            return null;
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }
}
