package com.jumia.task.core.validator;

import java.util.HashMap;

public class CountryNameShouldBeValid implements BaseValidator {
    private final String countryName;
    private final HashMap<String,String> validCountries;

    public CountryNameShouldBeValid(String countryName, HashMap<String,String> validCountries) {
        this.countryName = countryName;
        this.validCountries = validCountries;
    }

    @Override
    public void orThrow() throws IllegalArgumentException {
        if (!validCountries.containsKey(countryName)){
            throw new IllegalArgumentException("Country name is invalid");
        }
    }
}
