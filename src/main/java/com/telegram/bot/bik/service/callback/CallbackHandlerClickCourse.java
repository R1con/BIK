package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.enums.CallbackNameEnum;
import com.telegram.bot.bik.model.entity.Group;
import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.repository.GroupRepository;
import com.telegram.bot.bik.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.enums.CallbackNameEnum.MY_GROUP_SCHEDULE;
import static com.telegram.bot.bik.enums.CallbackNameEnum.OTHER_GROUP_SCHEDULE;

@Service
@RequiredArgsConstructor
public class CallbackHandlerClickCourse implements HandleCallback {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String course = callbackQuery.getData().split("/")[1];
        String group = callbackQuery.getData().split("/")[2];
        Long chatId = message.getChatId();

        Student student = new Student();
        student.setCourse(course);
        student.setTelegramId(chatId);
        student.setGroup(getGroup(group));
        studentRepository.save(student);
        return SendMessage.builder()
                .text("Вас приветствует бот, позволящий просмотреть расписание " +
                        "Белгородского индустриального колледжа. ")
                .chatId(message.getChatId())
                .replyMarkup(buildKeyboard())
                .build();
    }

    private Group getGroup(String name) {
        return groupRepository.findByName(name)
                .orElseThrow();
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text("Моя группа")
                                .callbackData(MY_GROUP_SCHEDULE.toString())
                                .build()
                )
        );

        buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text("Другие группы")
                                .callbackData(OTHER_GROUP_SCHEDULE.toString())
                                .build()
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
