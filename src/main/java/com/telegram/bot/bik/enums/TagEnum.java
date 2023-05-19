package com.telegram.bot.bik.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TagEnum {
    TD("td"),
    TR("tr"),
    MODERNSMALL("modernsmall"),
    SHADOW("shadow"),
    BG_COLOR("bgcolor");

    private final String name;
}
