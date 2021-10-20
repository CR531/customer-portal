package com.example.customerportal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CustomerPortalApplication.class)
public class CustomerPortalApplicationTests {

	@Test
	public void test(){
		CustomerPortalApplication.main(new String[]{});
	}
}
