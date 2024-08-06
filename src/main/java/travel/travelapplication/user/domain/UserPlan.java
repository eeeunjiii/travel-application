package travel.travelapplication.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import travel.travelapplication.constant.Status;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.plan.domain.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Document("UserPlan")
@Getter
public class UserPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Long budget;

    private String city;
    private String district; // 여행 지역 (여행 정보 입력)
  
    private Status status; // public, private

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private List<Place> places = new LinkedList<>();

    @DBRef
    private List<Route> routes = new ArrayList<>();

    @PersistenceCreator
    public UserPlan() {

    }

    @PersistenceCreator
    @Builder
    public UserPlan(String name, LocalDate startDate, LocalDate endDate, Long budget, String city, String district,
                    Status status, List<Place> places, List<Route> routes) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.city=city;
        this.district=district;
        this.status = status;
        this.places = places;
        this.routes = routes;
    }

    public void addPlaces(Place place) {
        this.places.add(place);
    }


    public UserPlan updateUserPlan(String name, Status status) {
        this.name=name;
        this.status=status;
        return this;
    }
}
