package com.example.demo.plan.place;

import com.example.demo.plan.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "city_county_district")
    private Long location;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;


    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Long tagId;

    @Column
    private Long cost;

    @Column
    private LocalDateTime availableTime;

}
