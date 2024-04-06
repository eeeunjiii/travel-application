package travel.travelapplication.dto.user;

import lombok.Getter;

@Getter
public class UserDto {

    private final String username;

    public UserDto(String username) {
        this.username = username;
    }
}
