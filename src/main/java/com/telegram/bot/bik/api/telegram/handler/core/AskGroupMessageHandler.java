package com.telegram.bot.bik.api.telegram.handler.core;

import com.telegram.bot.bik.enums.StateEnum;
import com.telegram.bot.bik.repository.StateRepository;
import com.telegram.bot.bik.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class AskGroupMessageHandler implements MessageHandler {
    private final StateService stateService;

    @Override
    public BotApiMethod<?> handle(Message message) {
        nextState(message.getChatId());
        return SendMessage.builder()
                .text("Введите название группы\n" +
                        "Формат: 42 ИСиП-П")
                .chatId(message.getChatId())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    @Override
    public void previousState() {

    }

    @Override
    public void nextState(Long telegramId) {
        stateService.save(telegramId, StateEnum.END_ASK_GROUP_NAME);
    }



    @Override
    public StateEnum getState() {
        return StateEnum.START_ASK_GROUP_NAME;
    }
}
