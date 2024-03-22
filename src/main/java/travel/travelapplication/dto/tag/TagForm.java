package travel.travelapplication.dto.tag;

import lombok.Getter;

@Getter
public class TagForm {

    private final String name;

    public TagForm(String name) {
        this.name = name;
    }
}
