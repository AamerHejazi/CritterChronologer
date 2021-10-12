package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return convertCustomerToCustomerDTO(
                customerService.createNewCustomer(
                        new Customer(customerDTO.getId(), customerDTO.getName(),
                                customerDTO.getPhoneNumber(), customerDTO.getNotes(),
                                petService.getAllPetsByPetsIds(customerDTO.getPetIds()))));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        //old code
        /*List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOList.add(convertCustomerToCustomerDTO(customer));
        }
        return customerDTOList;*/
        // new code using collections
        return customerService.getAllCustomers().stream()
                .map(this::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {

        return convertCustomerToCustomerDTO(petService.getOwnerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        return convertEmployeeToEmployeeDTO(employeeService.createNewEmployee(new Employee(employeeDTO.getId(),
                employeeDTO.getName(), employeeDTO.getSkills(), employeeDTO.getDaysAvailable())));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        return convertEmployeeToEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.findEmployeeAvailability(employeeDTO.getDate(), employeeDTO.getSkills())
                .stream()
                .map(this::convertEmployeeToEmployeeDTO)
                .collect(Collectors.toList());
    }


    public CustomerDTO convertCustomerToCustomerDTO(Customer customer) {

        return new CustomerDTO(customer.getId(),customer.getName(),customer.getPhoneNumber(),
                customer.getNotes(),customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {

        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSkills(), employee.getDaysAvailable());
    }
}
