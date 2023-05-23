package com.telegram.bot.bik.api.telegram.commands;

import com.telegram.bot.bik.enums.CommandEnum;
import com.telegram.bot.bik.model.entity.Group;
import com.telegram.bot.bik.repository.GroupRepository;
import com.telegram.bot.bik.repository.StudentRepository;
import com.telegram.bot.bik.service.MenuSelectGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.telegram.bot.bik.enums.CallbackNameEnum.CLICK_GROUP_NAME;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Component
@RequiredArgsConstructor
public class HandleCommandStart implements HandleCommand {

    private static final Set<String> COMMANDS = Set.of(CommandEnum.START.getCommand());
    private static final int SIZE_KEYBOARD_ROW = 4;

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final MenuSelectGroup menuSelectGroup;

    @Override
    public BotApiMethod<?> buildMessageByCommand(Message message)  {
        if (studentRepository.existsByTelegramId(message.getChatId())) {
            // TODO: 22.05.2023 build menu keyboard
        }
        var groups = groupRepository.findAll()
                .stream()
                .map(Group::getName)
                .toList();

        return SendMessage.builder()
                .text("Выберите группу:")
                .chatId(message.getChatId())
                .replyMarkup(buildKeyboard(groups))
                .parseMode(ParseMode.MARKDOWN)
                .build();

    }

    private InlineKeyboardMarkup buildKeyboard(List<String> groups) {
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        for (String specialization : groups) {
            row1.add(buildInlineButton(CLICK_GROUP_NAME + "/" + specialization, specialization));

            if (row1.size() == SIZE_KEYBOARD_ROW) {
                generateNewRow(allButtons, row1);
            }
        }

        if (row1.size() < SIZE_KEYBOARD_ROW) {
            allButtons.add(row1);
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(allButtons)
                .build();
    }

    private static void generateNewRow(List<List<InlineKeyboardButton>> allButtons, List<InlineKeyboardButton> row1) {
        List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
        allButtons.add(buttons);
        row1.clear();
    }

    @Override
    public Collection<String> getSupportedCommand() {
        return COMMANDS;
    }
}
