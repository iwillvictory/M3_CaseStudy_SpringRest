package com.codegym.controllers;

import com.codegym.models.Department;
import com.codegym.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    ///////////GET CUSTOMER ////////////

    @RequestMapping(value = "/departments/",method = RequestMethod.GET)
    public ResponseEntity<List<Department>> listAllDepartment(){
        List<Department> listDepartment= departmentService.findAll();
        if(listDepartment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listDepartment,HttpStatus.OK);
    }

    @RequestMapping(value = "/departments/{id}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartment(@PathVariable Integer id){
        System.out.println("Fetching Department with id " + id);
        Department department= departmentService.findById(id);
        if(department ==null){
            System.out.println("Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department,HttpStatus.OK);
    }

    ////////// CREATE OR UPDATE CUSTOMER///////
    @RequestMapping(value="/departments/", method = RequestMethod.POST)
    public ResponseEntity<Void> createDepartment(@RequestBody Department department, UriComponentsBuilder uriBuilder){
        System.out.println("Create a new Department");
        departmentService.save(department);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(uriBuilder.path("/departments/").buildAndExpand().toUri());
        return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/departments/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Integer id, @RequestBody Department department) {
        System.out.println("Updating Department " + id);

        Department currentDepartment = departmentService.findById(id);

        if (currentDepartment == null) {
            System.out.println("Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentDepartment.setDepartmentName(department.getDepartmentName());

        departmentService.save(currentDepartment);
        return new ResponseEntity<>(currentDepartment, HttpStatus.OK);
    }


    //////////DELETE CUSTOMER //////////////
    @RequestMapping(value = "/departments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Department with id " + id);

        Department department = departmentService.findById(id);
        if (department == null) {
            System.out.println("Unable to delete. Department with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
