package com.jumia.task.core.model;

public class PhoneNumberDTO {
    private String phoneNumber;
    private String countryName;
    private String countryCode;
    private boolean valid;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String phoneNumber, String countryName, String countryCode, boolean valid) {
        this.phoneNumber = phoneNumber;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.valid = valid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
