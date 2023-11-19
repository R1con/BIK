package com.telegram.bot.bik.api.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface HandleCommand {
    BotApiMethod<?> handle(Message message);
    String getSupportedCommand();
}
