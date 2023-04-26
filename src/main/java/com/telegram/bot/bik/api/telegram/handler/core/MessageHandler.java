package com.telegram.bot.bik.api.telegram.handler.core;

import com.telegram.bot.bik.enums.StateEnum;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    BotApiMethod<?> handle(Message message);
    void previousState();
    void nextState(Long telegramId);
    StateEnum getState();
}
