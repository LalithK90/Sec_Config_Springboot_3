package com.springsecurity3withthymeleaf.util.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommonService {
  private final DateTimeAgeService dateTimeAgeService;
  private final BCryptPasswordEncoder bcryptPasswordEncoder;

  public Integer numberAutoGen(String lastNumber) {
    int newNumber;
    int previousNumber;
    int newNumberFirstTwoCharacters;

    int currentYearLastTwoNumber =
        Integer.parseInt(String.valueOf(dateTimeAgeService.getCurrentDate().getYear()).substring(2, 4));

    if ( lastNumber != null ) {
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

    public void printAttributesInObject(Object obj) {
      Class< ? > objClass = obj.getClass();
      Field[] fields = objClass.getDeclaredFields();

      for ( Field field : fields ) {
        field.setAccessible(true);
        String name = field.getName();
        Object value;
        try {
          value = field.get(obj);
        } catch ( IllegalAccessException e ) {
                value = null;
            }
            System.out.println(name + " = " + value);
        }
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if ( email == null )
            return false;
        return pat.matcher(email).matches();
    }

    public boolean urlValidate(String urlString) {
        try {
            // create a URL object
            URL url = new URL(urlString);

          // check if the URL is valid
          url.toURI();

          return true;

        } catch ( MalformedURLException | IllegalArgumentException | URISyntaxException e ) {
          // catch the exceptions that may occur if the URL is not valid
          return false;
        }
    }

  public String generateAlias(int length) {
    // Define the length of the random string
    if ( length < 0 ) {
      length = 6;
    }
    String year = String.valueOf(LocalDateTime.now().getYear());
    // Define the characters to use in the random string
    String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Generate a random string of the specified length
    StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < length; i++ ) {
      int index = (int) (Math.random() * characters.length());
      sb.append(characters.charAt(index));
    }

    return sb.toString();
  }

  public String generateEncryptCode(int length) {
    return bcryptPasswordEncoder.encode(generateAlias(length));
  }

}