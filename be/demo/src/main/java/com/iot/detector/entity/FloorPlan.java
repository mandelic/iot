package com.iot.detector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "floorplan")
public class FloorPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(name="data", columnDefinition="bytea")
    private byte[] data;

    @ManyToMany(mappedBy = "floorPlans")
    Set<UserGroup> userGroups = new HashSet<>();
}
