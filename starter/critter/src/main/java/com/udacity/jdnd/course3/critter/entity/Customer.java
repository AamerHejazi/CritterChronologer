package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;
    private String phoneNumber;

    @Column(length = 512)
    private String notes;

    // added to solve recursion problem with plant class
    @JsonManagedReference
    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;
}
