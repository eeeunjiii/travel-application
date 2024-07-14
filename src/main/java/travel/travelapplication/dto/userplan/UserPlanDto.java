package travel.travelapplication.dto.userplan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travel.travelapplication.constant.Status;
import travel.travelapplication.user.domain.UserPlan;

import java.time.LocalDate;

public class UserPlanDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserPlanInfoDto {
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long budget;
        private String city;
        private String district;
        private Status status;

        @Builder
        public UserPlanInfoDto(String name, LocalDate startDate, LocalDate endDate, Long budget,
                               String city, String district, Status status) {
            this.name=name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.budget = budget;
            this.city=city;
            this.district=district;
            this.status=status;
        }

        public UserPlan toEntity() {
            return UserPlan.builder()
                    .name(name)
                    .startDate(startDate)
                    .endDate(endDate)
                    .budget(budget)
                    .city(city)
                    .district(district)
                    .status(status)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateUserPlanInfoDto {
        private String name;
        private Status status;

        public UpdateUserPlanInfoDto(String name, Status status) {
            this.name = name;
            this.status = status;
        }
    }
}
