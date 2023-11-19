package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.api.parser.ParserNameSpecialization;
import com.telegram.bot.bik.config.properties.ButtonProperties;
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
import java.util.List;
import java.util.Set;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.*;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@RequiredArgsConstructor
public class OtherGroupSchedule implements HandleCallback {

    private static final int SIZE_KEYBOARD_ROW = 4;

    private final ParserNameSpecialization parserNameSpecialization;
    private final MessageProperties messageProperties;
    private final ButtonProperties buttonProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        var specialties = parserNameSpecialization.parseSpecialization();
        Message message = callbackQuery.getMessage();

        return MessageBuilder.buildEditMessageText(messageProperties.getStartTitle() +
                "Выберите специальность на которой учитесь.", message.getChatId(), message.getMessageId(), buildStartKeyboard(specialties));
    }


    private InlineKeyboardMarkup buildStartKeyboard(Set<String> specialties) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        for (String specialization : specialties) {
            row1.add(buildInlineButton(SPECIALIZATION + "/" + specialization, specialization));

            if (row1.size() == SIZE_KEYBOARD_ROW) {
                generateNextRow(specializationButtons, row1);
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            specializationButtons.add(row1);
        }

        row2.add(
                buildInlineButton(MENU_SELECT_GROUP.toString(), buttonProperties.getBack())
        );

        specializationButtons.add(row2);

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
        return OTHER_GROUP_SCHEDULE;
    }
}
