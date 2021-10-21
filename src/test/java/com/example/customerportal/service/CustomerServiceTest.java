package com.example.customerportal.service;

import com.example.customerportal.graphql.repository.CustomerRepository;
import com.example.customerportal.model.Address;
import com.example.customerportal.model.Customer;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.model.CustomerObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CustomerServiceTest.class)
@SpringBootApplication
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {

    CustomerService customerService;
    @Mock
    CustomerService customerServiceImpl;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    Customer customer = null;

    @Test
    public void getCustomerDetails_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository, jdbcTemplate);
        List<CustomerObj> customerObjs = new ArrayList<>();
        CustomerObj customerObj = new CustomerObj();
        customerObj.setCustomer_id("123");
        customerObjs.add(customerObj);

        Mockito.when(jdbcTemplate.query(Mockito.anyString(), (BeanPropertyRowMapper) Mockito.any(Object.class))).thenReturn(customerObjs);

        List<CustomerObj> customerObjList = customerServiceImpl.getCustomerDetails();

        Assertions.assertEquals(customerObjList.get(0).getCustomer_id(), "123");
    }

    @Test
    public void getAllCustomers_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository, jdbcTemplate);
        List<CustomerObj> customerObjs = new ArrayList<>();
        CustomerObj customerObj = new CustomerObj();
        customerObj.setCustomer_id("123");
        customerObjs.add(customerObj);

        Mockito.when(jdbcTemplate.query(Mockito.anyString(), (BeanPropertyRowMapper) Mockito.any(Object.class), Mockito.any())).thenReturn(customerObjs);

        List<CustomerObj> customerObjList = customerServiceImpl.getAllCustomers(1);

        Assertions.assertEquals(customerObjList.get(0).getCustomer_id(), "123");
    }

    @Test
    public void getAddessbyCustomerId_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository, jdbcTemplate);
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setAddress_id("test");
        addresses.add(address);
        Mockito.when(jdbcTemplate.query(Mockito.anyString(), (BeanPropertyRowMapper) Mockito.any(Object.class), Mockito.any())).thenReturn(addresses);

        List<Address> addressList = customerServiceImpl.getAddessbyCustomerId("test");
        Assertions.assertEquals(addressList.get(0).getAddress_id(), "test");
    }

    @Test
    public void getCustomerbyId_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository, jdbcTemplate);
        CustomerObj customerObj = new CustomerObj();
        customerObj.setCustomer_id("123");

        Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), (BeanPropertyRowMapper) Mockito.any(Object.class), Mockito.any())).thenReturn(customerObj);

        CustomerObj customerObj1 = customerServiceImpl.getCustomerbyId("test");

        Assertions.assertEquals(customerObj1.getCustomer_id(), "123");
    }

    @Test
    public void getRandomCustomerId_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository);
        String str = customerServiceImpl.getRandomCustomerId();
        Assertions.assertEquals((str.length() > 0), true);
    }

    @Test
    public void createCustomer_ok() throws Throwable {
        customerServiceImpl = new CustomerServiceImpl(customerRepository, jdbcTemplate);

        customer = new Customer("1", "1", "1", "1", 1, "1");
        Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyLong(), Mockito.anyString(), Mockito.any(Customer.class))).thenReturn(1);

        CustomerCreated customer = customerServiceImpl.createCustomer("1", "1", "1", 1, "1");

        Assertions.assertEquals(customer.getCustomer_id(), null);
    }
}
