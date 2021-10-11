package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        return convertScheduleToScheduleDTO(
                scheduleService.createNewSchedule(
                        new Schedule(
                scheduleDTO.getId(),
                scheduleDTO.getDate(),
                scheduleDTO.getActivities(),
                employeeService.findAllById(scheduleDTO.getEmployeeIds()),
                petService.getAllByIds(scheduleDTO.getPetIds())
        )));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return  scheduleService.getAllSchedule()
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getAllScheduleIdsByPetId(petId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getAllScheduleByEmployeeId(employeeId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return  scheduleService.getAllScheduleForCustomer(customerId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }


    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        List<Long> employeeId = schedule.getEmployee()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        List<Long> petId = schedule.getPet()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());

        return new ScheduleDTO(schedule.getId(),employeeId,petId,schedule.getDate(),schedule.getActivities());
    }
}
