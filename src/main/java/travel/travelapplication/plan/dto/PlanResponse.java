package travel.travelapplication.plan.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.UserPlan;

@Getter
@RequiredArgsConstructor
public class PlanResponse {
    private ObjectId id;
    private UserPlan userPlan;

}
