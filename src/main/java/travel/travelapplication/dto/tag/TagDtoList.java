package travel.travelapplication.dto.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TagDtoList {

    private List<String> tagList = new LinkedList<>();
}
