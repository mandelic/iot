package com.iot.detector.repository;

import com.iot.detector.entity.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {
}
