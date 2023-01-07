package com.telegram.bot.bik.api.telegram.handler.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;


public abstract class AbstractMessageHandler implements Filter {
    private  Filter next;

    @Override
    public final void setNext(Filter next) {
        this.next = next;
    }

    @Override
    public final BotApiMethod<?> doMessage(Message messageUser) {
        return doMessageAndApplyNext(messageUser);
    }

    protected abstract BotApiMethod<?> doMessageAndApplyNext(Message messageUser);
}
