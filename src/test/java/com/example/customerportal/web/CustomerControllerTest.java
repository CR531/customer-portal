package com.example.customerportal.web;

import com.example.customerportal.CustomerPortalApplication;
import com.example.customerportal.controller.CustomerController;
import com.example.customerportal.model.Customer;
import com.example.customerportal.model.CustomerCreated;
import com.example.customerportal.model.CustomerObj;
import com.example.customerportal.service.CustomerService;
import com.example.customerportal.service.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = CustomerPortalApplication.class)
@WebMvcTest(CustomerController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerController;
    @MockBean
    CustomerServiceImpl customerServiceImpl;

    final String HEALTH = "/api/health";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void healthCheckTest() throws Exception {
        this.mockMvc.perform(get(HEALTH)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCustomers() throws Exception {
        List<CustomerObj> customerObjs = new ArrayList<CustomerObj>();
        Mockito.when(customerServiceImpl.getAllCustomers(Mockito.anyInt())).thenReturn(customerObjs);
        ResponseEntity<List<CustomerObj>> customerObjList = customerController.getCustomers(1);
        Assertions.assertEquals("200 OK", customerObjList.getStatusCode().toString());
    }

    @Test
    public void getCustomerbyCustomerId() throws Exception {
        CustomerObj customerObj = new CustomerObj();
        Mockito.when(customerServiceImpl.getCustomerbyId(Mockito.anyString())).thenReturn(customerObj);
        ResponseEntity<CustomerObj> customerObjList = customerController.getCustomerbyCustomerId("test");
        Assertions.assertEquals("200 OK", customerObjList.getStatusCode().toString());
    }

    @Test
    public void Customers() throws Exception {
        CustomerCreated customerCreated = new CustomerCreated();
        Customer customer = new Customer("1", "1", "1", "1", 1, "1");
        Mockito.when(customerServiceImpl.createCustomer(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyLong(), Mockito.anyString())).thenReturn(customerCreated);
        ResponseEntity<CustomerCreated> customerCreatedResponseEntity = customerController.Customers(customer);
        Assertions.assertEquals("200 OK", customerCreatedResponseEntity.getStatusCode().toString());
    }
}
