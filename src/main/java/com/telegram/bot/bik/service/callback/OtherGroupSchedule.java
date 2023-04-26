package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.ParserNameSpecialization;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.telegram.bot.bik.enums.CallbackNameEnum.MENU_SELECT_GROUP;
import static com.telegram.bot.bik.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;
import static com.telegram.bot.bik.enums.CallbackNameEnum.SPECIALIZATION;

@Service
@RequiredArgsConstructor
public class OtherGroupSchedule implements HandleCallback {
    private final ParserNameSpecialization parserNameSpecialization;

    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        var specialties = parserNameSpecialization.parseSpecialization();

        return EditMessageText.builder()
                .text("Вас приветствует бот, позволящий просмотреть расписание " +
                        "Белгородского индустриального колледжа. " +
                        "Выберите специальность на которой учитесь.")
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(buildStartKeyboard(specialties))
                .build();
    }


    private InlineKeyboardMarkup buildStartKeyboard(Set<String> specialties) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        for (String specialization : specialties) {
            row1.add(InlineKeyboardButton.builder()
                    .text(specialization)
                    .callbackData(SPECIALIZATION + "/" + specialization)
                    .build());

            if (row1.size() == 4) {
                List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
                specializationButtons.add(buttons);
                row1.clear();
            }
        }

        if (row1.size() < 4) {
            specializationButtons.add(row1);
        }

        row2.add(InlineKeyboardButton.builder()
                        .text("⬅️ Назад")
                        .callbackData(MENU_SELECT_GROUP.toString())
                .build()
        );

        specializationButtons.add(row2);

        return InlineKeyboardMarkup.builder()
                .keyboard(specializationButtons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return OTHER_GROUP_SCHEDULE;
    }
}
