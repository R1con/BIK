package com.telegram.bot.bik.service.callback.impl;

import com.telegram.bot.bik.config.properties.ButtonProperties;
import com.telegram.bot.bik.config.properties.MessageProperties;
import com.telegram.bot.bik.model.entity.Group;
import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.model.enums.CallbackNameEnum;
import com.telegram.bot.bik.repository.GroupRepository;
import com.telegram.bot.bik.repository.StudentRepository;
import com.telegram.bot.bik.service.callback.HandleCallback;
import com.telegram.bot.bik.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.model.enums.CallbackNameEnum.MY_GROUP_SCHEDULE;
import static com.telegram.bot.bik.model.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;
import static com.telegram.bot.bik.utils.ButtonBuilder.buildInlineButton;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class CallbackHandlerClickCourse implements HandleCallback {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final MessageProperties messageProperties;
    private final ButtonProperties buttonProperties;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String course = callbackQuery.getData().split("/")[1];
        String group = callbackQuery.getData().split("/")[2];
        Long chatId = message.getChatId();

        Student student = new Student();
        student.setCourse(course);
        student.setTelegramId(chatId);
        student.setGroup(getGroup(group));
        studentRepository.save(student);

        return MessageBuilder.buildSendMessage(message.getChatId(), messageProperties.getStartTitle(), buildKeyboard());
    }

    private Group getGroup(String name) {
        return groupRepository.findByName(name)
                .orElseThrow();
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(singletonList(
                        buildInlineButton(MY_GROUP_SCHEDULE.toString(), buttonProperties.getMyGroup())
                )
        );

        buttons.add(singletonList(
                        buildInlineButton(OTHER_GROUP_SCHEDULE.toString(), buttonProperties.getOtherGroup())
                )
        );

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }


    @Override
    public CallbackNameEnum getSupportedCallback() {
        return CallbackNameEnum.CLICK_COURSE;
    }
}
