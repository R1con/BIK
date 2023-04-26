package com.telegram.bot.bik.service;

import com.telegram.bot.bik.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.callback.HandleCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.enums.CallbackNameEnum.MENU_SELECT_GROUP;
import static com.telegram.bot.bik.enums.CallbackNameEnum.MY_GROUP_SCHEDULE;
import static com.telegram.bot.bik.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;

@Service
@RequiredArgsConstructor
public class MenuSelectGroup implements HandleCallback {
    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
         return EditMessageText.builder()
                .text("Вас приветствует бот, позволящий просмотреть расписание " +
                        "Белгородского индустриального колледжа. ")
                .chatId(message.getChatId())
                 .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(buildKeyboard())
                .build();
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text("Моя группа")
                                .callbackData(MY_GROUP_SCHEDULE.toString())
                                .build()
                )
        );

        buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text("Другие группы")
                                .callbackData(OTHER_GROUP_SCHEDULE.toString())
                                .build()
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
