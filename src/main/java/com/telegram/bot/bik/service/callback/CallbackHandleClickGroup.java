package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.enums.CallbackNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackHandleClickGroup implements HandleCallback {

    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        String group = callbackQuery.getData().split("/")[1];
        return SendMessage.builder()
                .text("Выберите курс:")
                .replyMarkup(buildKeyboard(group))
                .chatId(callbackQuery.getMessage().getChatId())
                .build();
    }

    private InlineKeyboardMarkup buildKeyboard(String group) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("1")
                        .callbackData(CallbackNameEnum.CLICK_COURSE + "/" + "1" + "/" + group)
                        .build()
        ));
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("2")
                        .callbackData(CallbackNameEnum.CLICK_COURSE + "/" + "2" + "/" + group)
                        .build()
        ));
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("3")
                        .callbackData(CallbackNameEnum.CLICK_COURSE + "/" + "3" + "/" + group)
                        .build()
        ));
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("4")
                        .callbackData(CallbackNameEnum.CLICK_COURSE + "/" + "4" + "/" + group)
                        .build()
        ));
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("5")
                        .callbackData(CallbackNameEnum.CLICK_COURSE + "/" + "5" + "/" + group)
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return CallbackNameEnum.CLICK_GROUP_NAME;
    }
}
