package com.example.customerportal.controller;

import com.example.customerportal.model.Customer;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.model.CustomerObj;
import com.example.customerportal.service.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.customerportal.service.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.List;


@RestController
@Validated
@RequestMapping("/api")
@Api(tags = "Customer Api Provider")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
//    @Autowired
//    CustomerServiceImpl customerServiceImpl;

    @GetMapping("/v1/customers")
    public ResponseEntity<List<CustomerObj>> getCustomers(@RequestParam(defaultValue = "10", required = true) int limit) {
        List<CustomerObj> customerObjs = new ArrayList<CustomerObj>();

        try {
            customerObjs = customerService.getAllCustomers(limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(customerObjs);
    }

    @GetMapping("/v1/customers/{customerId}")
    public ResponseEntity<CustomerObj> getCustomerbyCustomerId(
            @PathVariable String customerId) {
        CustomerObj customerObj = new CustomerObj();

        try {
            customerObj = customerService.getCustomerbyId(customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(customerObj);
    }

    @PostMapping("/v1/customers/")
    public ResponseEntity<CustomerCreated> Customers(
            @RequestBody Customer customer) {

        CustomerCreated customerCreated = null;

        try {
            customerCreated = customerService.createCustomer(customer.getCustomer_id(), customer.getFirst_name(), customer.getLast_name(), customer.getEmail_id(), customer.getPhone_number(), customer.getCreation_date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(customerCreated);
    }

}
