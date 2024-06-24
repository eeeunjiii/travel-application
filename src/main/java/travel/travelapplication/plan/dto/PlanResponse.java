package travel.travelapplication.plan.dto;

import lombok.Getter;
import org.bson.types.ObjectId;
import travel.travelapplication.user.domain.UserPlan;

@Getter
public class PlanResponse {
    private ObjectId id;
    private UserPlan userPlan;

    public PlanResponse(UserPlan userPlan) {
        this.userPlan = userPlan;
    }
}