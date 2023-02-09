package com.springsecurity3withthymeleaf.util.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final DateTimeAgeService dateTimeAgeService;
    public Integer numberAutoGen(String lastNumber) {
        int newNumber;
        int previousNumber;
        int newNumberFirstTwoCharacters;

        int currentYearLastTwoNumber =
                Integer.parseInt(String.valueOf(dateTimeAgeService.getCurrentDate().getYear()).substring(2, 4));

        if (lastNumber != null) {
            previousNumber = Integer.parseInt(lastNumber.substring(0, 9));
            newNumberFirstTwoCharacters = Integer.parseInt(lastNumber.substring(0, 2));

            if (currentYearLastTwoNumber == newNumberFirstTwoCharacters) {
                newNumber = previousNumber + 1;
            } else {
                newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000000");
            }

        } else {
            newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000000");
        }
        return newNumber;
    }

    public String phoneNumberLengthValidator(String number) {
        if (number.length() == 9) {
            number = "0".concat(number);
        }
        return number;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


}