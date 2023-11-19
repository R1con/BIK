package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.api.parser.ParserGroup;
import com.telegram.bot.bik.config.properties.MessageProperties;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.callback.HandleCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.CLICK_COURSE;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;
import static com.telegram.bot.bik.utils.MessageBuilder.buildSendMessage;

@Service
@RequiredArgsConstructor
public class CallbackHandleClickGroup implements HandleCallback {

    private static final int SIZE_KEYBOARD_ROW = 4;

    private final ParserGroup parserGroup;
    private final MessageProperties messageProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String group = callbackQuery.getData().split("/")[1];
        List<String> groups = parserGroup.parse(group);

        return buildSendMessage(callbackQuery.getMessage().getChatId(), messageProperties.getChooseCourse(), buildKeyboard(groups, group));
    }

    private InlineKeyboardMarkup buildKeyboard(List<String> groups, String group) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        for (String specialization : groups) {
            row1.add(buildInlineButton(CLICK_COURSE + "/" + specialization + "/" + group, specialization));

            if (row1.size() == SIZE_KEYBOARD_ROW) {
                generateNextRow(specializationButtons, row1);
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            specializationButtons.add(row1);
        }


        return InlineKeyboardMarkup.builder()
                .keyboard(specializationButtons)
                .build();
    }

    private void generateNextRow(List<List<InlineKeyboardButton>> specializationButtons, List<InlineKeyboardButton> row1) {
        List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
        specializationButtons.add(buttons);
        row1.clear();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return CallbackNameEnum.CLICK_GROUP_NAME;
    }
}
