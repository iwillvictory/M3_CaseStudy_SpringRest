package com.codegym.controllers;

import com.codegym.models.Employee;
import com.codegym.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    ///////////GET CUSTOMER ////////////

    @RequestMapping(value = "/employees/",method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployee(){
        List<Employee> listEmployee= employeeService.findAll();
        if(listEmployee.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listEmployee,HttpStatus.OK);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id){
        System.out.println("Fetching Employee with id " + id);
        Employee employee= employeeService.findById(id);
        if(employee ==null){
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    ////////// CREATE OR UPDATE CUSTOMER///////
    @RequestMapping(value="/employees/", method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder uriBuilder){
        System.out.println("Create a new Employee");
        employeeService.save(employee);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(uriBuilder.path("/employees/").buildAndExpand().toUri());
        return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        System.out.println("Updating Employee " + id);

        Employee currentEmployee = employeeService.findById(id);

        if (currentEmployee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentEmployee.setEmployeeName(employee.getEmployeeName());
        currentEmployee.setDepartment(employee.getDepartment());
        currentEmployee.setJoinDate(employee.getJoinDate());
        currentEmployee.setMail(employee.getMail());

        employeeService.save(currentEmployee);
        return new ResponseEntity<>(currentEmployee, HttpStatus.OK);
    }


    //////////DELETE CUSTOMER //////////////
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Employee with id " + id);

        Employee employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Unable to delete. Employee with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employeeService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
