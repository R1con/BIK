package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.CurrentScheduleGroup;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.bot.bik.enums.CallbackNameEnum.MENU_SELECT_GROUP;

@Service
@RequiredArgsConstructor
public class CallbackMySchedule implements HandleCallback {

    private final StudentRepository studentRepository;
    private final CurrentScheduleGroup currentScheduleGroup;


    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();

        Student student = studentRepository.findStudentByTelegramId(chatId).orElseThrow();
        String course = student.getCourse();
        String name = student.getGroup().getName();

        String scheduleGroup = currentScheduleGroup.parseSchedule(course, name);

        return EditMessageText.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .text(scheduleGroup)
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(buildKeyboard())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    private InlineKeyboardMarkup buildKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("⬅️ Назад")
                        .callbackData(MENU_SELECT_GROUP.toString())
                        .build()
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
