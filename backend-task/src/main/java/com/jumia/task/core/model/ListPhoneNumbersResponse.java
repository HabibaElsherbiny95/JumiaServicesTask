package com.jumia.task.core.model;

import java.util.List;

public class ListPhoneNumbersResponse {

    private List<PhoneNumberDTO> phoneNumbersList;

    public ListPhoneNumbersResponse() {
    }

    public ListPhoneNumbersResponse(List<PhoneNumberDTO> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;
    }


    public List<PhoneNumberDTO> getPhoneNumbersList() {
        return phoneNumbersList;
    }

    public void setPhoneNumbersList(List<PhoneNumberDTO> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;

    }
}
