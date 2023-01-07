package com.telegram.bot.bik.api.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.Collection;

public interface HandleCommand {
    BotApiMethod<?> buildMessageByCommand(Message message);
    Collection<String> getSupportedCommand();
}
