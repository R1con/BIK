package com.telegram.bot.bik.service;

import com.telegram.bot.bik.config.properties.MessageProperties;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.callback.HandleCallback;
import com.telegram.bot.bik.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.*;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@RequiredArgsConstructor
public class MenuSelectGroup implements HandleCallback {

    private final MessageProperties messageProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        return MessageBuilder.buildEditMessageText(messageProperties.getStartTitle(), message.getChatId(),
                message.getMessageId(), buildKeyboard());
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(
                        buildInlineButton(MY_GROUP_SCHEDULE.toString(), messageProperties.getMyGroup())
                )
        );

        buttons.add(Collections.singletonList(
                        buildInlineButton(OTHER_GROUP_SCHEDULE.toString(), messageProperties.getOtherGroup())
                )
        );

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return MENU_SELECT_GROUP;
    }
}
