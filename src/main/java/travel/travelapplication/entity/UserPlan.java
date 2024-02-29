package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("UserPlan")
@Getter
public class UserPlan {

    @Id
    private ObjectId id;

    private String name;

    private Date startDate;
    private Date endDate;

    private Long budget;

    @Enumerated(EnumType.STRING)
    private Status status; // public, private

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private List<Place> places = new ArrayList<>();

    @DBRef
    private List<Route> routes = new ArrayList<>();

    public UserPlan(String name, Date startDate, Date endDate, Long budget, Status status,
                    List<Place> places, List<Route> routes) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = status;
        this.places = places;
        this.routes = routes;
    }
}
