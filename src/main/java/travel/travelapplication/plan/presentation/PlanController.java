package travel.travelapplication.plan.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.dto.plan.CommentDto;
import travel.travelapplication.dto.plan.ReplyDto;
import travel.travelapplication.plan.application.CommentService;
import travel.travelapplication.plan.domain.Comment;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.application.PlanService;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plans")
@Slf4j
public class PlanController {
    private final PlanService planService;
    private final PlanRepository planRepository;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/community")
    public String allPlans() throws IllegalAccessException {

        return "html/community";
    }

    @GetMapping("/community/{planId}")
    public String plan(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                       @PathVariable("planId") ObjectId planId, Model model) throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);

        Plan plan = planService.findById(planId);
        List<Plan> savedPlans = user.getSavedPlans();
        List<Comment> comments = plan.getComments();

        model.addAttribute("plan", plan);
        model.addAttribute("savedPlans", savedPlans);
        model.addAttribute("comments", comments);
        model.addAttribute("commentDto", new CommentDto());

        return "test/plan";
    }

    @PostMapping("/community/save/{planId}")
    public ResponseEntity<Boolean> savePlanToUser(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                                                  @PathVariable("planId") ObjectId planId)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        Plan plan = planService.findById(planId);

        boolean isSaved = planService.saveSelectedPlan(user, plan);

        return ResponseEntity.ok(isSaved);
    }

    @PostMapping("/community/comment/{planId}")
    public ResponseEntity<CommentDto> saveCommentToPlan(@PathVariable("planId") ObjectId planId,
                                                        @RequestBody CommentDto commentDto) {
        Plan plan = planService.findById(planId);

        planService.saveCommentToPlan(plan, commentDto);

        log.info("commentDto.content: {}", commentDto.getContent());
        log.info("commentDto.email: {}", commentDto.getEmail());

        return ResponseEntity.ok(commentDto);
    }

    @PostMapping("/community/reply/{commentId}")
    public ResponseEntity<ReplyDto> saveReplyToComment(@PathVariable("commentId") ObjectId commentId,
                                                       @RequestBody ReplyDto replyDto) {
        Comment comment = commentService.findById(commentId);
        commentService.saveReply(comment, replyDto);

        log.info("replyDto.content: {}", replyDto.getContent());

        return ResponseEntity.ok(replyDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> findPlan(@RequestParam(name = "id") ObjectId id) {
        Plan plan = planService.findById(id);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@RequestParam(name = "id") ObjectId id) {
    }

    @GetMapping("/search") // 특정 장소가 포함된 지도 조회 (키워드가 없으면 모두 출력)
    public String planView(Model model,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "target", required = false) String target) { // target: map, place 구분 (select)

        if (keyword != null) {
            List<Plan> findPlans = planService.searchByPlace(keyword);
            model.addAttribute("findPlans", findPlans);
        } else {
            List<Plan> plans = planService.findAll();
            model.addAttribute("plans", plans);
        }
        return null;
    }
}
