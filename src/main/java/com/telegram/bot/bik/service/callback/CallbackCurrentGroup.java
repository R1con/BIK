package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.CurrentScheduleGroup;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.enums.CallbackNameEnum.GROUPS_BY_SPESIALIZATION;
import static com.telegram.bot.bik.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackCurrentGroup implements HandleCallback {
    private final CurrentScheduleGroup currentScheduleGroup;
    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        String idGroup = callbackQuery.getData().split("/")[1];
        String group = callbackQuery.getData().split("/")[2];
        var scheduleGroup = currentScheduleGroup.parseSchedule(idGroup, group);
        return EditMessageText.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .text(scheduleGroup)
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(buildKeyboard())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData(OTHER_GROUP_SCHEDULE.toString())
                        .build()
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
