package travel.travelapplication.dto.userplan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPlanInfoRequest {
    private String email;
    private String city;
    private String district;
    private long period;
}
