package com.example.customerportal.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMutation implements GraphQLMutationResolver {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    public CustomerCreated createCustomer(final String first_name, final String last_name, final String email_id, final long phone_number, final String creation_date) {
        return this.customerServiceImpl.createCustomer(first_name, last_name, email_id, phone_number, creation_date);
    }
}
