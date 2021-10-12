package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    // 1
    public List<Pet> getAllPetsByPetsIds(List<Long> pets) {

        List<Pet> petsList = new ArrayList<>();

        if (pets != null && !pets.isEmpty()) {
            petsList = new ArrayList<>();
            for (Long petId : pets) {
                petsList.add(petRepository.getOne(petId));
            }
        }

        return petsList;
    }

    // 2
    public Customer getOwnerByPetId(long petId) {

        return petRepository.getOne(petId).getOwner();
    }

    //3
    public List<Pet> getAllByIds(List<Long> petIdes) {
        return new ArrayList<>(petRepository.findAllById(petIdes));
    }

    // 4
    public Pet createNewPet(Pet pet) {
        Customer customer = customerRepository.save(pet.getOwner());
        System.out.println("" + customer.getId());
        List<Pet> petsList = new ArrayList<>();

        pet.setOwner(customer);
        pet = petRepository.save(pet);

        petsList.add(pet);
        customer.setPets(petsList);

        customerRepository.save(customer);
        return pet;
    }

    // 5
    public Customer findCustomerById(Long id) {

        return customerRepository.getOne(id);
    }

    // 6
    public Pet findPetById(long id) {
        return petRepository.getOne(id);
    }

    // 7
    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findAllByCustomerId(long customerId) {

        return petRepository.findPetByOwnerId(customerId);
    }
}
