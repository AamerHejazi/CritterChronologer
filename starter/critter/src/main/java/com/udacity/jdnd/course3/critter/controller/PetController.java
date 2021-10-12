package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        return convertPetToPetDTO(petService.createNewPet(
                new Pet(petDTO.getId(), petDTO.getType(),
                        petDTO.getName(), petDTO.getBirthDate(),
                        petDTO.getNotes(),
                        petService.findCustomerById(petDTO.getOwnerId()))));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return  convertPetToPetDTO(petService.findPetById(petId)) ;
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.findAllPets()
                .stream().map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.findAllByCustomerId(ownerId)
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    public PetDTO convertPetToPetDTO(Pet pet) {

        return new PetDTO(pet.getId(),pet.getType(),pet.getName(),pet.getOwner().getId(),pet.getBirthDate(),pet.getNotes());
    }

}
