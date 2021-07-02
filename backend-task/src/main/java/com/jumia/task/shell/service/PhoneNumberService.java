package com.jumia.task.shell.service;

import com.jumia.task.core.model.ListPhoneNumbersRequest;
import com.jumia.task.core.model.ListPhoneNumbersResponse;
import com.jumia.task.core.processor.ListPhoneNumbersProcessor;
import com.jumia.task.core.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {
    private final CustomerRepository customerRepository;

    public PhoneNumberService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ListPhoneNumbersResponse listPhoneNumbers(ListPhoneNumbersRequest request) {
        return new ListPhoneNumbersProcessor(request, customerRepository).execute();
    }


}
