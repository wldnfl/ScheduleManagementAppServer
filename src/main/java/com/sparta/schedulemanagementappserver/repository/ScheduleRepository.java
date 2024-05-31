package com.sparta.schedulemanagementappserver.repository;

import com.sparta.schedulemanagementappserver.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    Optional<Schedule> findById(Object scheduleId);
//
//    void save(Schedule schedule);
//
//    List<Schedule> findAll();
//
//    void delete(Schedule schedule);
}
