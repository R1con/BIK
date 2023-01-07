package com.telegram.bot.bik.api.telegram.handler.impl;

import com.telegram.bot.bik.api.telegram.handler.core.AbstractMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Order(0)
@Slf4j
public class QuestionGroupFilter extends AbstractMessageHandler {
    public BotApiMethod<Message> doMessageAndApplyNext(Message messageUser) {
        log.info(messageUser.getText() + " this message is command start");
        return SendMessage.builder().text("Введите группу").chatId(messageUser.getChatId()).build();
    }
}
