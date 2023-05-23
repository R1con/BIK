package com.telegram.bot.bik.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@UtilityClass
public class ButtonBuilder {

    public static InlineKeyboardButton buildInlineButton(String callback, String text) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callback)
                .build();
    }
}
