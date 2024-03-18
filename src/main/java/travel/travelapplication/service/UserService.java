package travel.travelapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.entity.Tag;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    public User updateUserName(String email, String username) throws IllegalAccessException{
        User user=userRepository.findByEmail(email)
                .orElse(null);

        if(user!=null) {
            return user.updateUserName(username);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void addTag(String email, Tag tag) throws IllegalAccessException {
        User user=userRepository.findByEmail(email)
                .orElse(null);
        if(user!=null) {
            user.addTag(tag);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public List<Tag> findAllTag(String email) throws IllegalAccessException {
        User user=userRepository.findByEmail(email)
                .orElse(null);
        if(user!=null) {
            return user.getTags();
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }
}
