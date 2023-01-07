package com.telegram.bot.bik.api.telegram.commands;

import com.telegram.bot.bik.api.parser.ParserNameSpecialization;
import com.telegram.bot.bik.enums.CommandEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.telegram.bot.bik.enums.CallbackNameEnum.SPECIALIZATION;

@Component
@RequiredArgsConstructor
public class HandleCommandStart implements HandleCommand {
    public static final Set<String> COMMANDS = Set.of(CommandEnum.START.getCommand());
    private final ParserNameSpecialization parserNameSpecialization;

    @Override
    public BotApiMethod<?> buildMessageByCommand(Message message)  {
        var specialties = parserNameSpecialization.parseSpecialization();
        InlineKeyboardMarkup inlineKeyboardMarkup = buildStartKeyboard(specialties);

        return SendMessage.builder()
                .text("Вас приветствует бот, позволящий просмотреть расписание " +
                        "Белгородского индустриального колледжа. " +
                        "Выберите специальность на которой учитесь.")
                .chatId(message.getChatId())
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    private InlineKeyboardMarkup buildStartKeyboard(Set<String> specialties) {
        List<List<InlineKeyboardButton>> specializationButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();

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


        return InlineKeyboardMarkup.builder()
                .keyboard(specializationButtons)
                .build();
    }

    @Override
    public Collection<String> getSupportedCommand() {
        return COMMANDS;
    }
}
