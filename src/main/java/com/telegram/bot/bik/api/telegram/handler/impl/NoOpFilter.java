package com.telegram.bot.bik.api.telegram.handler.impl;

import com.telegram.bot.bik.api.telegram.handler.core.Filter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class NoOpFilter  implements Filter {
    @Override
    public void setNext(Filter next) {
    }

    @Override
    public BotApiMethod<?> doMessage(Message messageUser) {
        return SendMessage.builder().chatId(messageUser.getChatId()).text(messageUser.getText()).build();
    }
}
