package travel.travelapplication.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.user.application.UserPlanService;

@RestController
@RequiredArgsConstructor
public class UserPlanController {
    private final UserPlanService userPlanService;


}
