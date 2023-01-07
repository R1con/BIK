package com.telegram.bot.bik.api.telegram.handler.core;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Filter extends ChainElement<Filter> {
    BotApiMethod<?> doMessage(Message messageUser);
}
