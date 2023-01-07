package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.ConnectionToSite;
import com.telegram.bot.bik.api.parser.ParserGroup;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.HandleCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.telegram.bot.bik.enums.CallbackNameEnum.GROUPS_BY_SPESIALIZATION;
import static com.telegram.bot.bik.enums.CallbackNameEnum.SPECIALIZATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackGeneratorGroupByName implements HandleCallback {
    public static final int SIZE_KEYBOARD_ROW = 4;
    private final ParserGroup parserGroup;
    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        String nameGroup = callbackQuery.getData().split("/")[1];
        List<String> groups = parserGroup.parse(nameGroup);

        return EditMessageText.builder()
                .text("Hellow " + nameGroup)
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(buildStartKeyboard(groups, nameGroup))
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }



    private InlineKeyboardMarkup buildStartKeyboard(List<String> specialties, String nameGroup) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        for (String specialization : specialties) {
            row1.add(InlineKeyboardButton.builder()
                    .text(specialization)
                    .callbackData(GROUPS_BY_SPESIALIZATION + "/" + specialization + "/" + nameGroup)
                    .build());

            if (row1.size() == SIZE_KEYBOARD_ROW) {
                List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
                specializationButtons.add(buttons);
                row1.clear();
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            specializationButtons.add(row1);
        }


        return InlineKeyboardMarkup.builder()
                .keyboard(specializationButtons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return SPECIALIZATION;
    }
}
