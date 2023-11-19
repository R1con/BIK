package com.telegram.bot.bik.api.telegram;

import com.telegram.bot.bik.api.telegram.commands.HandleCommand;
import com.telegram.bot.bik.config.properties.TelegramProperties;
import com.telegram.bot.bik.exception.ConnectionSiteException;
import com.telegram.bot.bik.service.callback.HandleCallback;
import com.telegram.bot.bik.service.factory.CallbackFactory;
import com.telegram.bot.bik.service.factory.CommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    
    private final TelegramProperties telegramProperties;
    private final CallbackFactory callbackFactory;
    private final CommandFactory commandFactory;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            if (isCommand(message)) {
                handleCommand(message);
            }
        }
    }

    private static boolean isCommand(Message message) {
        return message.getText().startsWith("/");
    }

    private void handleCommand(Message message) throws TelegramApiException {
        HandleCommand command = commandFactory.getCommand(message.getText().split("/")[1]);
        execute(command.handle(message));
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        String data = callbackQuery.getData();

        log.info("[CALLBACK] callback = {} pressed by {} with tgId = {}", data, callbackQuery.getFrom().getUserName(), callbackQuery.getMessage().getChatId());
        try {
            HandleCallback callback = callbackFactory.getCallback(data.split("/")[0]);
            execute(callback.handle(callbackQuery));
        } catch (ConnectionSiteException e) {
            execute(SendMessage.builder()
                    .text("Возникла ошибка при подключении к сайту. Зайдите позже")
                    .chatId(callbackQuery.getFrom().getId())
                    .build());
        }
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
