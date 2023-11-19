package com.telegram.bot.bik.validation;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DateValidation implements Validable {

    private static final String PATTERN_START_DATE = "\"^\\d{2}\\.\\d{2}\\.\\d{4}.*\"";
    private static final Pattern PATTERN_DATE = Pattern.compile(PATTERN_START_DATE);

    @Override
    public boolean validate(String date) {
        Matcher matcher = PATTERN_DATE.matcher(date);
        return matcher.matches();
    }
}
