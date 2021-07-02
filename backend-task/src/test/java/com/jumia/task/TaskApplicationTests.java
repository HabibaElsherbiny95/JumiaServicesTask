package com.jumia.task;

import com.jumia.task.core.model.Customer;
import com.jumia.task.core.model.ListPhoneNumbersRequest;
import com.jumia.task.core.model.ListPhoneNumbersResponse;
import com.jumia.task.core.model.State;
import com.jumia.task.core.processor.ListPhoneNumbersProcessor;
import com.jumia.task.core.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TaskApplicationTests {

	private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
	@Test
	void contextLoads() {
	}

	@Test
	void ListPhoneNumbersWithInvalidCountryNameThrows() {

		Customer mozambiqueCustomer = new Customer(1, "customerName 1", "(258) 848826725");
		Customer moroccoCustomer = new Customer(2, "customerName 2", "(212) 633963130");
		Customer invalidCustomer = new Customer(2, "customerName 2", "(258) 84330678235");

		List<Customer> allCustomers = new ArrayList();
		allCustomers.add(mozambiqueCustomer);
		allCustomers.add(moroccoCustomer);
		allCustomers.add(invalidCustomer);

		Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

		ListPhoneNumbersRequest request = new ListPhoneNumbersRequest("germany");

		try {
			ListPhoneNumbersResponse response = new ListPhoneNumbersProcessor(request, customerRepository).execute();
		} catch (IllegalArgumentException e) {} ;
	}

	@Test
	void ListPhoneNumbersWithNoFiltersWorks() {

		Customer mozambiqueCustomer = new Customer(1, "customerName 1", "(258) 848826725");
		Customer moroccoCustomer = new Customer(2, "customerName 2", "(212) 633963130");
		Customer invalidCustomer = new Customer(2, "customerName 2", "(258) 84330678235");

		List<Customer> allCustomers = new ArrayList();
		allCustomers.add(mozambiqueCustomer);
		allCustomers.add(moroccoCustomer);
		allCustomers.add(invalidCustomer);

		Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

		ListPhoneNumbersRequest request = new ListPhoneNumbersRequest();

		ListPhoneNumbersResponse response = new ListPhoneNumbersProcessor(request, customerRepository).execute();

		Assertions.assertEquals(3, response.getPhoneNumbersList().size());

		Assertions.assertEquals(mozambiqueCustomer.getPhone(), response.getPhoneNumbersList().get(0).getPhoneNumber());
		Assertions.assertEquals("258", response.getPhoneNumbersList().get(0).getCountryCode());
		Assertions.assertEquals("mozambique", response.getPhoneNumbersList().get(0).getCountryName());
		Assertions.assertEquals(true, response.getPhoneNumbersList().get(0).isValid());

		Assertions.assertEquals(moroccoCustomer.getPhone(), response.getPhoneNumbersList().get(1).getPhoneNumber());
		Assertions.assertEquals("212", response.getPhoneNumbersList().get(1).getCountryCode());
		Assertions.assertEquals("morocco", response.getPhoneNumbersList().get(1).getCountryName());
		Assertions.assertEquals(true, response.getPhoneNumbersList().get(1).isValid());

		Assertions.assertEquals(invalidCustomer.getPhone(), response.getPhoneNumbersList().get(2).getPhoneNumber());
		Assertions.assertEquals(false, response.getPhoneNumbersList().get(2).isValid());
	}

	@Test
	void ListPhoneNumbersWithCountryFiltersWorks() {

		Customer mozambiqueCustomer = new Customer(1, "customerName 1", "(258) 848826725");
		Customer moroccoCustomer = new Customer(2, "customerName 2", "(212) 633963130");
		Customer invalidCustomer = new Customer(2, "customerName 2", "(258) 84330678235");

		List<Customer> allCustomers = new ArrayList();
		allCustomers.add(mozambiqueCustomer);
		allCustomers.add(moroccoCustomer);
		allCustomers.add(invalidCustomer);

		Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

		ListPhoneNumbersRequest request = new ListPhoneNumbersRequest("morocco");

		ListPhoneNumbersResponse response = new ListPhoneNumbersProcessor(request, customerRepository).execute();

		Assertions.assertEquals(1, response.getPhoneNumbersList().size());

		Assertions.assertEquals(moroccoCustomer.getPhone(), response.getPhoneNumbersList().get(0).getPhoneNumber());
		Assertions.assertEquals("212", response.getPhoneNumbersList().get(0).getCountryCode());
		Assertions.assertEquals("morocco", response.getPhoneNumbersList().get(0).getCountryName());
		Assertions.assertEquals(true, response.getPhoneNumbersList().get(0).isValid());
	}

	@Test
	void ListPhoneNumbersWithValidFiltersWorks() {

		Customer mozambiqueCustomer = new Customer(1, "customerName 1", "(258) 848826725");
		Customer moroccoCustomer = new Customer(2, "customerName 2", "(212) 633963130");
		Customer invalidCustomer = new Customer(2, "customerName 2", "(258) 84330678235");

		List<Customer> allCustomers = new ArrayList();
		allCustomers.add(mozambiqueCustomer);
		allCustomers.add(moroccoCustomer);
		allCustomers.add(invalidCustomer);

		Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

		ListPhoneNumbersRequest request = new ListPhoneNumbersRequest(State.VALID);

		ListPhoneNumbersResponse response = new ListPhoneNumbersProcessor(request, customerRepository).execute();

		Assertions.assertEquals(2, response.getPhoneNumbersList().size());

		Assertions.assertEquals(mozambiqueCustomer.getPhone(), response.getPhoneNumbersList().get(0).getPhoneNumber());
		Assertions.assertEquals("258", response.getPhoneNumbersList().get(0).getCountryCode());
		Assertions.assertEquals("mozambique", response.getPhoneNumbersList().get(0).getCountryName());
		Assertions.assertEquals(true, response.getPhoneNumbersList().get(0).isValid());

		Assertions.assertEquals(moroccoCustomer.getPhone(), response.getPhoneNumbersList().get(1).getPhoneNumber());
		Assertions.assertEquals("212", response.getPhoneNumbersList().get(1).getCountryCode());
		Assertions.assertEquals("morocco", response.getPhoneNumbersList().get(1).getCountryName());
		Assertions.assertEquals(true, response.getPhoneNumbersList().get(1).isValid());
	}
}
