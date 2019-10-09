package com.codegym.controllers;

import com.codegym.models.Customer;
import com.codegym.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    ///////////GET CUSTOMER ////////////

    @RequestMapping(value = "/customers/",method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listAllCustomer(){
        List<Customer> listCustomer= customerService.findAll();
        if(listCustomer.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Customer>>(listCustomer,HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        System.out.println("Fetching Customer with id " + id);
        Customer customer= customerService.findById(id);
        if(customer ==null){
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer,HttpStatus.OK);
    }

    ////////// CREATE OR UPDATE CUSTOMER///////
    @RequestMapping(value="/customers/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer, UriComponentsBuilder uriBuilder){
        System.out.println("Create a new Customer");
        customerService.save(customer);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(uriBuilder.path("/customers/").buildAndExpand().toUri());
        return new ResponseEntity<Void>(httpHeaders,HttpStatus.CREATED);
    }

    //////////DELETE CUSTOMER //////////////


}
