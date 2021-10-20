package com.example.customerportal.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.customerportal.model.CustomerObj;
import com.example.customerportal.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerQuery implements GraphQLQueryResolver {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    public List<CustomerObj> customerDetails() {
        return this.customerServiceImpl.getCustomerDetails();
    }

    public List<CustomerObj> getCustomers(final int count) {
        return this.customerServiceImpl.getAllCustomers(count);
    }

    public CustomerObj customerbyId(final String customer_id) {
        return this.customerServiceImpl.getCustomerbyId(customer_id);
    }


//customersByAddress(addressFilter: AddressFilter): [CustomerObj]
//    public List<CustomerObj> customersByAddress() {
//        return this.customerService.getCustomersByAddress();
//    }
}
