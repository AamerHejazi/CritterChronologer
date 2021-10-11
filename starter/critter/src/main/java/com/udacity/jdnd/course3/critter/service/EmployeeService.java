package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee createNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {

        Employee employee = employeeRepository.getOne(employeeId);
        Employee employee1 = new Employee(employee.getId(), employee.getName(), employee.getSkills(), daysAvailable);
        employeeRepository.save(employee1);
    }

    public List<Employee> findEmployeeAvailability(LocalDate date, Set<EmployeeSkill> skills) {
        return employeeRepository.findByDaysAvailable(date.getDayOfWeek())
                .stream()
                .filter(availableEmployee -> availableEmployee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    public List<Employee> findAllById(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }
}
