package com.telegram.bot.bik.api.telegram.handler.core;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractMessageHandler implements Filter{
    private Filter next;

    public AbstractMessageHandler() {
    }

    public void setNext(Filter next) {
        this.next = next;
    }

    public final BotApiMethod<?> doMessage(Message messageUser) {
        this.doMessageAndApplyNext(messageUser).getMethod();
        return this.next.doMessage(messageUser);
    }

    protected abstract BotApiMethod<Message> doMessageAndApplyNext(Message messageUser);
}
