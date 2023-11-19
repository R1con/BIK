package com.telegram.bot.bik.api.telegram.commands.impl;

import com.telegram.bot.bik.api.telegram.commands.HandleCommand;
import com.telegram.bot.bik.config.properties.MessageProperties;
import com.telegram.bot.bik.model.entity.Group;
import com.telegram.bot.bik.model.enums.CommandEnum;
import com.telegram.bot.bik.repository.StudentRepository;
import com.telegram.bot.bik.service.interfaces.GroupService;
import com.telegram.bot.bik.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.CLICK_GROUP_NAME;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Component
@RequiredArgsConstructor
public class HandleCommandStart implements HandleCommand {

    private static final int SIZE_KEYBOARD_ROW = 4;

    private final GroupService groupService;
    private final StudentRepository studentRepository;
    private final MessageProperties messageProperties;

    @Override
    public BotApiMethod<?> handle(Message message)  {
        if (studentRepository.existsByTelegramId(message.getChatId())) {
            // TODO: 22.05.2023 build menu keyboard
        }
        var groups = groupService.findAll()
                .stream()
                .map(Group::getName)
                .toList();

        return MessageBuilder.buildSendMessage(message.getChatId(), messageProperties.getChooseGroup(), buildKeyboard(groups), ParseMode.MARKDOWN);
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

    private void generateNewRow(List<List<InlineKeyboardButton>> allButtons, List<InlineKeyboardButton> row1) {
        List<InlineKeyboardButton> buttons = new ArrayList<>(row1);
        allButtons.add(buttons);
        row1.clear();
    }

    @Override
    public String getSupportedCommand() {
        return CommandEnum.START.getCommand();
    }
}
