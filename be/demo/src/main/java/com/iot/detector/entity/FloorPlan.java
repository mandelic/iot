package com.iot.detector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private UUID assetId;

    @Column(name="data", columnDefinition="bytea")
    private byte[] data;

    @ManyToMany(mappedBy = "floorPlans")
    Set<UserGroup> userGroups = new HashSet<>();

    @ManyToMany(mappedBy = "floorPlans")
    Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getFloorPlans().add(this);
    }
}
