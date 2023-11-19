package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface HandleCallback {
    BotApiMethod<?> handle(CallbackQuery callbackQuery);
    CallbackNameEnum getSupportedCallback();
}
