package entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import travel.travelapplication.constant.Status;
import travel.travelapplication.entity.Plan;
import travel.travelapplication.entity.SavedPlan;
import travel.travelapplication.entity.User;
import travel.travelapplication.entity.UserPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityTest {

    User user1 =User.builder()
            .name("spring")
            .build();

    User user2 = User.builder()
            .name("spring2")
            .build();

    UserPlan userPlan1= UserPlan.builder()
            .user(user1)
            .status(Status.PUBLIC)
            .build();

    UserPlan userPlan2 = UserPlan.builder()
            .user(user1)
            .status(Status.PUBLIC)
            .build();

    Plan plan1 = Plan.builder()
            .name("plan1")
            .userPlan(userPlan1)
            .build();

    Plan plan2 = Plan.builder()
            .name("plan2")
            .userPlan(userPlan2)
            .build();

    SavedPlan savedPlan1 = SavedPlan.builder()
            .user(user2)
            .plan(plan1)
            .build();

    SavedPlan savedPlan2 = SavedPlan.builder()
            .user(user2)
            .plan(plan2)
            .build();


    @Test
    void entityTest() {
        List<SavedPlan> savedPlanList = new ArrayList<>();

        if(Objects.equals(savedPlan1.getUser().getName(), "spring2")) {
            savedPlanList.add(savedPlan1);
        }

        Assertions.assertThat(savedPlanList.size()).isEqualTo(1);
    }
}
