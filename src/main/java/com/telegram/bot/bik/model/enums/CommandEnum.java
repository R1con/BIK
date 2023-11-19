package com.telegram.bot.bik.model.enums;

import lombok.Getter;

public enum CommandEnum {
    START("start");

    @Getter
    private final java.lang.String command;

    CommandEnum(java.lang.String command) {
        this.command = command;
    }
}
