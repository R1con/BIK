package com.telegram.bot.bik.validation;

public interface Validable {
    boolean validate(String date);
    default boolean  validate() {
        return true;
    }
}
