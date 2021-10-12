package com.udacity.jdnd.course3.critter.entity;



import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Employee> employee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private List<Pet> pet;
}
