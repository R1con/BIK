package com.telegram.bot.bik.api.telegram.handler.impl;

import com.telegram.bot.bik.api.telegram.handler.core.Filter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Null Object design
 */
@Slf4j
public class NullObjectFilter implements Filter {
    @Override
    public void setNext(Filter next) {
    }

    @Override
    public BotApiMethod<?> doMessage(Message messageUser) {
        log.info("null object is called");
        return SendMessage.builder().chatId(messageUser.getChatId()).text(messageUser.getText()).build();
    }
}
