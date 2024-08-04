package travel.travelapplication.place.domain;

import lombok.Getter;
import lombok.Setter;
import travel.travelapplication.auth.dto.SessionUser;

@Getter
@Setter
public class Recommendation {

    private Long userid;
    private Long placeId;
    private String name;
    private String address;
    private Long predict_lasso;

    public SessionUser toSessionUser() {
        return SessionUser.builder()
                .placeId(placeId)
                .name(name)
                .address(address)
                .predict_lasso(predict_lasso)
                .build();
    }
}
