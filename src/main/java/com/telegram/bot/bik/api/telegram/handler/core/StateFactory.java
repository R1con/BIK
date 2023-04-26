package com.telegram.bot.bik.api.telegram.handler.core;

import com.telegram.bot.bik.enums.StateEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StateFactory {
    public static final Map<StateEnum, MessageHandler> MESSAGE_HANDLER_MAP = new HashMap<>();

    public StateFactory(List<MessageHandler> messageHandlers) {
        messageHandlers.forEach(messageHandler -> MESSAGE_HANDLER_MAP.put(messageHandler.getState(), messageHandler));
    }

    public MessageHandler getMessageHandler(StateEnum state) {
        return MESSAGE_HANDLER_MAP.get(state);
    }
}
