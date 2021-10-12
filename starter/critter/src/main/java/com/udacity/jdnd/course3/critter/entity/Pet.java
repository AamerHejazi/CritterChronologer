package com.udacity.jdnd.course3.critter.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PetType type;
    private String name;
    private LocalDate birthDate;
    @Column(length = 512)
    private String notes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Customer owner;

}
