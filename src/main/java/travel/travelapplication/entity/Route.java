package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Map<Long, Place> placeMap; // 경로의 시작, 종료 지점의 장소 + 인근 추천 장소
    private Map<Long, Transportation> transMap;

    @ManyToOne
    @JoinColumn(name = "userPlan_id")
    private UserPlan userPlan;

    @ManyToMany
    @JoinTable(name = "ROUTE_TRANSPORT",
        joinColumns = @JoinColumn(name = "ROUTE_ID"),
        inverseJoinColumns = @JoinColumn(name = "TRANSPORT_ID"))
    private List<Transportation> transportationList;

    @Builder
    public Route(Map<Long, Place> placeMap, Map<Long, Transportation> transMap,
                 UserPlan userPlan, List<Transportation> transportationList) {
        this.placeMap = placeMap;
        this.transMap = transMap;
        this.userPlan = userPlan;
        this.transportationList = transportationList;
    }
}
