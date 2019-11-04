package com.codegym.controllers;

import com.codegym.models.Department;
import com.codegym.models.Employee;
import com.codegym.models.EmployeeForm;
import com.codegym.services.DepartmentService;
import com.codegym.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin("*")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

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
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeForm employee, UriComponentsBuilder uriBuilder){
        System.out.println(employee.toString());
        System.out.println("Create a new Employee");
        Department department = departmentService.findById(employee.getDepartment());
        if(department == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee emp = new Employee();
        emp.setDepartment(department);
        emp.setMail(employee.getMail());
        emp.setJoinDate(employee.getJoinDate());
        emp.setEmployeeName(employee.getEmployeeName());
        employeeService.save(emp);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(uriBuilder.path("/employees/").buildAndExpand().toUri());
        return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employees/", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee( @RequestBody EmployeeForm employee) {

        Department department = departmentService.findById(employee.getDepartment());
        Employee currentEmployee = employeeService.findById(employee.getEmployeeId());

        if (currentEmployee == null || department == null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        currentEmployee.setEmployeeId(employee.getEmployeeId());
        currentEmployee.setEmployeeName(employee.getEmployeeName());
        currentEmployee.setDepartment(department);
        currentEmployee.setJoinDate(employee.getJoinDate());
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
