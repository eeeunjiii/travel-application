package travel.travelapplication.dto.userplan;

import lombok.Getter;
import travel.travelapplication.constant.Status;

import java.util.Date;

public class UserPlanDto {

    @Getter
    public static class UserPlanInfoDto { // TODO: 여행하고자 하는 지역 추가
        private String name;
        private Date startDate;
        private Date endDate;
        private Long budget;

        public UserPlanInfoDto(String name, Date startDate, Date endDate, Long budget) {
            this.name=name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.budget = budget;
        }
    }

    @Getter
    public static class UpdateUserPlanInfoDto {
        private String name;
        private Status status;

        public UpdateUserPlanInfoDto(String name, Status status) {
            this.name = name;
            this.status = status;
        }
    }
}
