package com.jumia.task.core.model;

public class ListPhoneNumbersRequest {
    private String countryName = null;
    private State state = State.NONE;

    public ListPhoneNumbersRequest() {}

    public ListPhoneNumbersRequest(String countryName, State state) {
        this.countryName = countryName;
        this.state = state;
    }

    public ListPhoneNumbersRequest(String countryName) {
        this.countryName = countryName;
    }
    public ListPhoneNumbersRequest(State state) {
        this.state = state;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
