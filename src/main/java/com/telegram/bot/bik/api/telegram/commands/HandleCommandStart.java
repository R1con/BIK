package com.telegram.bot.bik.api.telegram.commands;

import com.telegram.bot.bik.api.telegram.handler.core.MessageHandler;
import com.telegram.bot.bik.api.telegram.handler.core.StateFactory;
import com.telegram.bot.bik.enums.CommandEnum;
import com.telegram.bot.bik.enums.StateEnum;
import com.telegram.bot.bik.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HandleCommandStart implements HandleCommand {
    public static final Set<String> COMMANDS = Set.of(CommandEnum.START.getCommand());

    private final StateService stateService;
    private final StateFactory stateFactory;

    @Override
    public BotApiMethod<?> buildMessageByCommand(Message message)  {
        stateService.save(message.getChatId(), StateEnum.START_ASK_GROUP_NAME);
        MessageHandler messageHandler = stateFactory.getMessageHandler(StateEnum.START_ASK_GROUP_NAME);

        return messageHandler.handle(message);

    }

    @Override
    public Collection<String> getSupportedCommand() {
        return COMMANDS;
    }
}
