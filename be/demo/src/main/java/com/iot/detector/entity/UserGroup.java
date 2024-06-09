package com.iot.detector.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_group", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "userGroups")
    Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "floor_plan_groups",
            joinColumns = @JoinColumn(name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "floor_plan_id")
    )
    private Set<FloorPlan> floorPlans = new HashSet<>();

    public void addFloorPlan(FloorPlan floorPlan) {
        this.floorPlans.add(floorPlan);
        floorPlan.getUserGroups().add(this);
    }

    public UserGroup(String name) {
        this.name = name;
    }
}
