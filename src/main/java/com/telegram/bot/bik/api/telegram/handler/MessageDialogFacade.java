package com.telegram.bot.bik.api.telegram.handler;

import com.telegram.bot.bik.api.telegram.handler.core.ChainElement;
import com.telegram.bot.bik.api.telegram.handler.core.Filter;
import com.telegram.bot.bik.api.telegram.handler.impl.NullObjectFilter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class MessageDialogFacade {
    private final Filter chainHead;

    public MessageDialogFacade(List<Filter> steps) {
        this.chainHead = ChainElement.buildChain(steps, new NullObjectFilter());
    }

    public BotApiMethod<?> doMessage(Message messageUser) {
        return chainHead.doMessage(messageUser);
    }
}
