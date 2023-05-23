package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.ParserGroup;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.enums.CallbackNameEnum.GROUPS_BY_SPESIALIZATION;
import static com.telegram.bot.bik.enums.CallbackNameEnum.SPECIALIZATION;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackGeneratorGroupByName implements HandleCallback {

    private static final int SIZE_KEYBOARD_ROW = 4;

    private final ParserGroup parserGroup;

    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        String nameGroup = callbackQuery.getData().split("/")[1];
        List<String> groups = parserGroup.parse(nameGroup);

        return EditMessageText.builder()
                .text("Выбранная группа: " + nameGroup)
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
            row1.add(buildInlineButton(GROUPS_BY_SPESIALIZATION + "/" + specialization + "/" + nameGroup, specialization));
            if (row1.size() == SIZE_KEYBOARD_ROW) {
                generateNextRow(specializationButtons, row1);
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            specializationButtons.add(row1);
        }


        return InlineKeyboardMarkup.builder()
                .keyboard(specializationButtons)
                .build();
    }

    private static void generateNextRow(List<List<InlineKeyboardButton>> specializationButtons, List<InlineKeyboardButton> row1) {
        List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
        specializationButtons.add(buttons);
        row1.clear();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return SPECIALIZATION;
    }
}
