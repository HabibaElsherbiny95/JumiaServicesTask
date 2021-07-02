package com.jumia.task.shell.controller;

import com.jumia.task.core.model.Customer;
import com.jumia.task.core.model.ListPhoneNumbersRequest;
import com.jumia.task.core.model.ListPhoneNumbersResponse;
import com.jumia.task.core.model.State;
import com.jumia.task.shell.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/phoneNumbers")
public class PhoneNumberController {
    @Autowired
    private PhoneNumberService phoneNumberService;

    @GetMapping
    ListPhoneNumbersResponse listPhoneNumbers(@RequestParam(required = false) String countryName,
                                              @RequestParam(required = false) State state ) {
        return phoneNumberService.listPhoneNumbers(new ListPhoneNumbersRequest(countryName, state));
    }
}
