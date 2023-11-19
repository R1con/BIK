package com.telegram.bot.bik.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@UtilityClass
public class MessageBuilder {

    public static SendMessage buildSendMessage(Long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    public static SendMessage buildSendMessage(Long chatId, String text, ReplyKeyboard keyboard, String parseMode) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .parseMode(parseMode)
                .build();
    }

    public static SendMessage buildSendMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }

    public static EditMessageText buildEditMessageText(String text, long chatId, int messageId, InlineKeyboardMarkup keyboard) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(messageId)
                .replyMarkup(keyboard)
                .build();
    }

    public static EditMessageText buildEditMessageText(String text, long chatId, int messageId, InlineKeyboardMarkup keyboard,
                                                       String parseMode) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(messageId)
                .parseMode(parseMode)
                .replyMarkup(keyboard)
                .build();
    }
}
