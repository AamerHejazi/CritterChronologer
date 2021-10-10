package com.udacity.jdnd.course3.critter.entity;



import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
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

    public Schedule(Long id, LocalDate date, Set<EmployeeSkill> activities, List<Employee> employee, List<Pet> pet) {
        this.id = id;
        this.date = date;
        this.activities = activities;
        this.employee = employee;
        this.pet = pet;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }
}
