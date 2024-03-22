package travel.travelapplication.dto.user;

import lombok.Getter;

@Getter
public class UserForm {

    private final String username;

    public UserForm(String username) {
        this.username = username;
    }
}
