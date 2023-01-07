package com.telegram.bot.bik.api.telegram.handler.impl;

import com.telegram.bot.bik.api.telegram.handler.core.AbstractMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
public class QuestionNameUserFilter extends AbstractMessageHandler {
    @Override
    public BotApiMethod<Message> doMessageAndApplyNext(Message messageUser) {
        log.info("this message saved group name and ask name user^ " + messageUser.getText());
        return SendMessage.builder().text("Введите имя").chatId(messageUser.getChatId()).build();
    }
}