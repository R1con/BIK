package com.telegram.bot.bik.api.telegram.handler.core;

import com.telegram.bot.bik.api.parser.ParserNameSpecialization;
import com.telegram.bot.bik.enums.StateEnum;
import com.telegram.bot.bik.service.StateService;
import com.telegram.bot.bik.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.telegram.bot.bik.enums.CallbackNameEnum.MY_GROUP_SCHEDULE;
import static com.telegram.bot.bik.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;
import static com.telegram.bot.bik.enums.CallbackNameEnum.SPECIALIZATION;

@Service
@RequiredArgsConstructor
public class SaveGroupMessageHandler implements MessageHandler {
    private final StudentServiceImpl service;
    private final ParserNameSpecialization parserNameSpecialization;


    @Override
    public BotApiMethod<?> handle(Message message) {
        String group = message.getText();
        service.save(message.getChatId(), group);

        return SendMessage.builder()
                .text("Вас приветствует бот, позволящий просмотреть расписание " +
                        "Белгородского индустриального колледжа. ")
                .chatId(message.getChatId())
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
    public void previousState() {

    }

    @Override
    public void nextState(Long telegramId) {

    }

    @Override
    public StateEnum getState() {
        return StateEnum.END_ASK_GROUP_NAME;
    }
}
