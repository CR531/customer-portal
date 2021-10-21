package com.example.customerportal.service;

import com.example.customerportal.model.Address;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.model.CustomerObj;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    public List<CustomerObj> getCustomerDetails();

    public List<CustomerObj> getAllCustomers(int count);

    public List<Address> getAddessbyCustomerId(String CustomerId);

    public CustomerObj getCustomerbyId(String id);

    public CustomerCreated createCustomer(String first_name, String last_name, String email_id, long phone_number, String creation_date);

    public String getRandomCustomerId();
}
