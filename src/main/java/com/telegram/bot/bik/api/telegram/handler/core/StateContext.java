package com.telegram.bot.bik.api.telegram.handler.core;

import com.telegram.bot.bik.model.entity.State;
import com.telegram.bot.bik.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StateContext {
    private final StateFactory stateFactory;
    private final StateService stateService;

    public BotApiMethod<?> getHandler(Long telegramId, Message message) {
        State state = stateService.get(telegramId);
        MessageHandler messageHandler = stateFactory.getMessageHandler(state.getName());
        messageHandler.nextState(telegramId);
        return messageHandler.handle(message);
    }
}
