package travel.travelapplication.dto.tag;

import lombok.Getter;

@Getter
public class TagDto {

    private final String name;

    public TagDto(String name) {
        this.name = name;
    }
}
