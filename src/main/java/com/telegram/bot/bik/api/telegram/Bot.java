package com.telegram.bot.bik.api.telegram;

import com.telegram.bot.bik.api.telegram.commands.HandleCommand;
import com.telegram.bot.bik.config.properties.TelegramProperties;
import com.telegram.bot.bik.map.CallbackMap;
import com.telegram.bot.bik.map.CommandMap;
import com.telegram.bot.bik.service.callback.HandleCallback;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final TelegramProperties telegramProperties;
    private final CallbackMap callbackMap;
    private final CommandMap commandMap;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            handleCallbackQeury(update);
        }else if (update.hasMessage()) {
            if (isCommand(message)) {
                handleCommand(message);
            }
        }
    }

    private static boolean isCommand(Message message) {
        return message.getText().startsWith("/");
    }

    private void handleCommand(Message message) throws TelegramApiException {
        HandleCommand command = commandMap.getCommand(message.getText().split("/")[1]);
        execute(command.buildMessageByCommand(message));
    }

    private void handleCallbackQeury(Update update) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();

        log.info("[CALLBACK] callback = {} pressed by {} with tgId = {}", data, callbackQuery.getFrom().getUserName(), callbackQuery.getMessage().getChatId());
        HandleCallback callback = callbackMap.getCallback(data.split("/")[0]);
        execute(callback.buildMessageByCallback(callbackQuery));
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }
}
