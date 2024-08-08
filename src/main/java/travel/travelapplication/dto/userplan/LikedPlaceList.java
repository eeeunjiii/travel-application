package travel.travelapplication.dto.userplan;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class LikedPlaceList {

    private List<String> likedPlaces=new LinkedList<>();
}
