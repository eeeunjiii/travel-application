package travel.travelapplication.place.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recommendation {

    private Long userid;
    private Long placeId;
    private String name;
    private String address;
    private Long predict_lasso;
}
