package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.api.parser.CurrentScheduleGroup;
import com.telegram.bot.bik.config.properties.ButtonProperties;
import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.repository.StudentRepository;
import com.telegram.bot.bik.service.callback.HandleCallback;
import com.telegram.bot.bik.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.MENU_SELECT_GROUP;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;

@Service
@RequiredArgsConstructor
public class CallbackMySchedule implements HandleCallback {

    private final StudentRepository studentRepository;
    private final CurrentScheduleGroup currentScheduleGroup;
    private final ButtonProperties buttonProperties;


    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        Long chatId = message.getChatId();

        Student student = studentRepository.findStudentByTelegramId(chatId).orElseThrow();
        String course = student.getCourse();
        String name = student.getGroup().getName();

        String scheduleGroup = currentScheduleGroup.parseSchedule(course, name);

        return MessageBuilder.buildEditMessageText(scheduleGroup, message.getChatId(),
                message.getMessageId(), buildKeyboard(), ParseMode.MARKDOWN);
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(
                buildInlineButton(MENU_SELECT_GROUP.toString(), buttonProperties.getBack())
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return CallbackNameEnum.MY_GROUP_SCHEDULE;
    }
}
