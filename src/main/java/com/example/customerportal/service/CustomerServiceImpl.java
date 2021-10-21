package com.example.customerportal.service;

import com.example.customerportal.graphql.repository.CustomerRepository;
import com.example.customerportal.model.Address;
import com.example.customerportal.model.Customer;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.model.CustomerObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerServiceImpl() {
    }

    /**
     * If postgres db is connected
     */

    @Transactional(readOnly = true)
    @Override
    public List<CustomerObj> getCustomerDetails() {
        List<CustomerObj> customerObjs = jdbcTemplate.query("SELECT * from customers", BeanPropertyRowMapper.newInstance(CustomerObj.class));

        for (CustomerObj customerObj : customerObjs) {
            customerObj.setAddressDetails(getAddessbyCustomerId(customerObj.getCustomer_id()));
        }
        return customerObjs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerObj> getAllCustomers(final int count) {
        List<CustomerObj> customerObjs = jdbcTemplate.query("SELECT * from customers LIMIT ?", BeanPropertyRowMapper.newInstance(CustomerObj.class), count);

        for (CustomerObj customerObj : customerObjs) {
            customerObj.setAddressDetails(getAddessbyCustomerId(customerObj.getCustomer_id()));
        }
        return customerObjs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Address> getAddessbyCustomerId(final String CustomerId) {
        return jdbcTemplate.query("SELECT * from address where customer_id = ?", BeanPropertyRowMapper.newInstance(Address.class), CustomerId);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerObj getCustomerbyId(final String id) {
        try {
            CustomerObj customerObj = jdbcTemplate.queryForObject("SELECT * FROM Customers WHERE customer_id=?",
                    BeanPropertyRowMapper.newInstance(CustomerObj.class), id);
            customerObj.setAddressDetails(getAddessbyCustomerId(customerObj.getCustomer_id()));
            return customerObj;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public CustomerCreated createCustomer(final String first_name, final String last_name, final String email_id, final long phone_number, final String creation_date) {
        final Customer customer = new Customer();
        String generatedString = getRandomCustomerId();
        customer.setCustomer_id(generatedString);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setEmail_id(email_id);
        customer.setPhone_number(phone_number);
        customer.setCreation_date(creation_date);
        int i = jdbcTemplate.update("INSERT INTO customers (customer_id, first_name, last_name, email_id, phone_number, creation_date) VALUES(?,?,?,?,?,?)",
                new Object[]{customer.getCustomer_id(), customer.getFirst_name(), customer.getLast_name(), customer.getEmail_id(), customer.getPhone_number(), customer.getCreation_date()});
        if (i != 0)
            return new CustomerCreated(customer.getCustomer_id());
        else
            return new CustomerCreated();
    }

    @Override
    public String getRandomCustomerId() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /**
     * If postgres db is not connected
     */
    /*
    @Transactional
    public CustomerCreated createCustomer(final String customer_id, final String first_name, final String last_name, final String email_id, final long phone_number, final String creation_date) {
        final Customer customer = new Customer();
        customer.setCustomer_id(customer_id);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setEmail_id(email_id);
        customer.setPhone_number(phone_number);
        customer.setCreation_date(creation_date);
        return new CustomerCreated(this.customerRepository.save(customer).getCustomer_id());
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers(final int count) {
        return this.customerRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Customer getCustomerbyId(final String id) {
        //TODO: need to add persistant logic to get customer by ID
        return null;
    }
    */
}
