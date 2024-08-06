package travel.travelapplication.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection = "User")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String name;

    private String email;

    private String role;

    @DBRef
    private List<UserPlan> userPlans = new ArrayList<>();

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    @DBRef
    private List<Place> likedPlaces = new ArrayList<>();

    @DBRef
    private List<SavedPlan> savedPlans = new ArrayList<>();

    private String refreshToken;

    private String accessToken;

    @PersistenceCreator
    public User() {
    }

    @PersistenceCreator
    @Builder
    public User(String name, String email, List<UserPlan> userPlans, List<Tag> tags,
                List<Place> likedPlaces, List<SavedPlan> savedPlans, String role, String accessToken) {
        this.name = name;
        this.email = email;
        this.userPlans = userPlans;
        this.tags = tags;
        this.likedPlaces = likedPlaces;
        this.savedPlans = savedPlans;
        this.role = role;
        this.accessToken = accessToken;
    }

    public void update(User updatedUser) {
        Optional.ofNullable(updatedUser.getName()).ifPresent(none -> this.name = updatedUser.getName());
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(none -> this.email = updatedUser.getEmail());
        Optional.ofNullable(updatedUser.getUserPlans()).ifPresent(none -> this.userPlans = updatedUser.getUserPlans());
        Optional.ofNullable(updatedUser.getTags()).ifPresent(none -> this.tags = updatedUser.getTags());
        Optional.ofNullable(updatedUser.getLikedPlaces())
                .ifPresent(none -> this.likedPlaces = updatedUser.getLikedPlaces());
        Optional.ofNullable(updatedUser.getSavedPlans())
                .ifPresent(none -> this.savedPlans = updatedUser.getSavedPlans());
        Optional.ofNullable(updatedUser.role).ifPresent(none -> this.role = updatedUser.getRole());
        Optional.ofNullable(updatedUser.accessToken).ifPresent(none -> this.role = updatedUser.getRole());
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
