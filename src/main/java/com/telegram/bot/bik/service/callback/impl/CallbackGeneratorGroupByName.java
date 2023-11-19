package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.api.parser.ParserGroup;
import com.telegram.bot.bik.config.properties.ButtonProperties;
import com.telegram.bot.bik.config.properties.MessageProperties;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.callback.HandleCallback;
import com.telegram.bot.bik.utils.ButtonBuilder;
import com.telegram.bot.bik.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.GROUPS_BY_SPESIALIZATION;
import static com.telegram.bot.bik.model.enums.CallbackNameEnum.SPECIALIZATION;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackGeneratorGroupByName implements HandleCallback {

    private static final int SIZE_KEYBOARD_ROW = 4;

    private final ParserGroup parserGroup;
    private final ButtonProperties buttonProperties;
    private final MessageProperties messageProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String nameGroup = callbackQuery.getData().split("/")[1];
        List<String> groups = parserGroup.parse(nameGroup);
        Message message = callbackQuery.getMessage();

        return MessageBuilder.buildEditMessageText(messageProperties.getSelectedGroup() + nameGroup, message.getChatId(), message.getMessageId(),
                buildStartKeyboard(groups, nameGroup), ParseMode.MARKDOWN);
    }


    private InlineKeyboardMarkup buildStartKeyboard(List<String> specialties, String nameGroup) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> otherButtons = new ArrayList<>();

        for (String specialization : specialties) {
            row1.add(buildInlineButton(GROUPS_BY_SPESIALIZATION + "/" + specialization + "/" + nameGroup, specialization));
            if (row1.size() == SIZE_KEYBOARD_ROW) {
                generateNextRow(specializationButtons, row1);
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            specializationButtons.add(row1);
        }

        otherButtons.add(ButtonBuilder.buildInlineButton(CallbackNameEnum.SPECIALIZATION.toString(), buttonProperties.getBack()));

        specializationButtons.add(otherButtons);


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
        return SPECIALIZATION;
    }
}
