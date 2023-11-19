package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.api.parser.CurrentScheduleGroup;
import com.telegram.bot.bik.config.properties.ButtonProperties;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.callback.HandleCallback;
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
import java.util.Collections;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.*;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackCurrentGroup implements HandleCallback {

    private final CurrentScheduleGroup currentScheduleGroup;
    private final ButtonProperties buttonProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String idGroup = callbackQuery.getData().split("/")[1];
        String group = callbackQuery.getData().split("/")[2];
        Message message = callbackQuery.getMessage();
        var scheduleGroup = currentScheduleGroup.parseSchedule(idGroup, group);

        return MessageBuilder.buildEditMessageText(scheduleGroup, message.getChatId(), message.getMessageId(),
                buildKeyboard(group),
                ParseMode.MARKDOWN);
    }

    private InlineKeyboardMarkup buildKeyboard(String groupName) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                buildInlineButton(CallbackNameEnum.SPECIALIZATION + "/" + groupName, buttonProperties.getCourse() + groupName),
                buildInlineButton(OTHER_GROUP_SCHEDULE.toString(), buttonProperties.getGroups())
        ));

        buttons.add(Collections.singletonList(
                buildInlineButton(MENU_SELECT_GROUP.toString(), buttonProperties.getMenu())
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return GROUPS_BY_SPESIALIZATION;
    }
}
