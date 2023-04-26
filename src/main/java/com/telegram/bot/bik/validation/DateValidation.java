package com.telegram.bot.bik.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DateValidation implements Validable {

    public static final String PATTERN_START_DATE = "\"^\\d{2}\\.\\d{2}\\.\\d{4}.*\"";
    private static final Pattern pattern = Pattern.compile(PATTERN_START_DATE);

    @Override
    public boolean validate(String date) {
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
