package com.example.demo.user;

import com.example.demo.common.BaseEntity;
import com.example.demo.plan.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "my_plan")
    private Long myPlanId;

    @ManyToOne
    @JoinColumn(name = "saved_plan")
    private Long savedPlanId;

    @ManyToOne
    @JoinColumn(name = "liked_place")
    private Long likedPlaceId;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

}
