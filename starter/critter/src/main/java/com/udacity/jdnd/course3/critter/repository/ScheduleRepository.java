package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> getSchedulesByPetId(Long petId);

    List<Schedule> getSchedulesByEmployeeId(Long employeeId);

    List<Schedule> findByPetIn(List<Pet> petEntitiesList);

}
