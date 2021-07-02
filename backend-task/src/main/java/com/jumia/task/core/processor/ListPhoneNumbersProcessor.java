package com.jumia.task.core.processor;

import com.jumia.task.core.model.*;
import com.jumia.task.core.repository.CustomerRepository;
import com.jumia.task.core.validator.CountryNameShouldBeValid;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class ListPhoneNumbersProcessor extends BaseProcessor<ListPhoneNumbersResponse> {

    private final ListPhoneNumbersRequest request;
    private final CustomerRepository customerRepository;
    private static final HashMap<String, String> countryPatterns;

    static {
        countryPatterns = new HashMap<>();
        countryPatterns.put("cameroon", "\\(237\\) ?[2368]\\d{7,8}$");
        countryPatterns.put("ethiopia", "\\(251\\) ?[1-59]\\d{8}$");
        countryPatterns.put("morocco", "\\(212\\) ?[5-9]\\d{8}$");
        countryPatterns.put("mozambique", "\\(258\\) ?[28]\\d{7,8}$");
        countryPatterns.put("uganda", "\\(256\\) ?\\d{9}$");
    }

    public ListPhoneNumbersProcessor(ListPhoneNumbersRequest request, CustomerRepository customerRepository) {
        this.request = request;
        this.customerRepository = customerRepository;
    }

    @Override
    void validate() {
        if (request.getCountryName() != null) {
            new CountryNameShouldBeValid(request.getCountryName().toLowerCase(), countryPatterns).orThrow();
        }
    }

    @Override
    ListPhoneNumbersResponse process() {
        List<PhoneNumberDTO> result = getPhoneNumbers();
        return new ListPhoneNumbersResponse(result);
    }

    private List<PhoneNumberDTO> getPhoneNumbers() {
        Iterable<Customer> allCustomers = customerRepository.findAll();
        Stream<String> phoneNumbers = StreamSupport.stream(allCustomers.spliterator(), false)
                .map(customer -> customer.getPhone());

        if (request.getCountryName() != null) {
            String countryPattern = countryPatterns.get(request.getCountryName().toLowerCase());
            phoneNumbers = phoneNumbers.filter(number -> number.matches(countryPattern));

        }
        if (request.getState() == State.VALID) {
            phoneNumbers = phoneNumbers.filter(number -> validatePhoneNumber(number));

        } else if (request.getState() == State.INVALID) {
            phoneNumbers = phoneNumbers.filter(number -> !validatePhoneNumber(number));

        }

        return phoneNumbers.map(number -> mapPhoneNumberDTO(number)).collect(Collectors.toList());
    }

    private PhoneNumberDTO mapPhoneNumberDTO(String number) {
        boolean valid = validatePhoneNumber(number);
        String countryCode = getCountryCode(number);
        String countryName = "";

        if (valid) {
            countryName = getCountryName(number);
        }
        return new PhoneNumberDTO(number, countryName, countryCode, valid);

    }

    private boolean validatePhoneNumber(String number) {
        Stream<Pattern> allCountryPatterns = countryPatterns.values().stream().map(item -> Pattern.compile(item));

        return allCountryPatterns.anyMatch(pattern -> pattern.matcher(number).matches());
    }

    private String getCountryName(String number) {
        Stream<Pattern> allCountryPatterns = countryPatterns.values().stream().map(item -> Pattern.compile(item));
        Stream<Pattern> matchedPattern = allCountryPatterns.filter(pattern -> pattern.matcher(number).matches());
        if (matchedPattern != null) {
            String matchedPatternString = matchedPattern.findFirst().get().toString();
            return countryPatterns.entrySet()
                    .stream()
                    .filter(pattern -> matchedPatternString.equals(pattern.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst().get();
        } else {
            return null;
        }
    }

    private String getCountryCode(String number) {
        String [] countryCode = number.split("\\)");

        return countryCode[0].substring(1,4);
    }
}