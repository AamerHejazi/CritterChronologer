package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Schedule createNewSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }


    public  List<Schedule>  getAllSchedule(){
        return scheduleRepository.findAll();
    }


    public List<Schedule> getAllScheduleIdsByPetId(long petId){

        //old code
        /* Pet pet = petRepository.getOne(petId);
        List<Schedule> scheduleList = scheduleRepository.getSchedulesByPet(pet);
        return scheduleList;*/
        // new code
        //return scheduleRepository.getSchedulesByPetId(petRepository.getOne(petId).getId());
        return scheduleRepository.getSchedulesByPetId(petId);
    }

    public List<Schedule> getAllScheduleByEmployeeId(long employeeId){

        return  scheduleRepository.getSchedulesByEmployeeId(employeeId);
    }


    public List<Schedule> getAllScheduleForCustomer(long customerId){

        return scheduleRepository
                .findByPetIn(customerRepository.getOne(customerId).getPets());
    }
}
