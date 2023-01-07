package com.telegram.bot.bik.service.callback;

import com.telegram.bot.bik.api.parser.CurrentScheduleGroup;
import com.telegram.bot.bik.enums.CallbackNameEnum;
import com.telegram.bot.bik.service.HandleCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.telegram.bot.bik.enums.CallbackNameEnum.GROUPS_BY_SPESIALIZATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallbackCurrentGroup implements HandleCallback {
    private final CurrentScheduleGroup currentScheduleGroup;
    @Override
    public BotApiMethod<?> buildMessageByCallback(CallbackQuery callbackQuery) {
        String idGroup = callbackQuery.getData().split("/")[1];
        String group = callbackQuery.getData().split("/")[2];
//        currentScheduleGroup()
        return null;
    }

    @Override
    public CallbackNameEnum getSupportedCallback() {
        return GROUPS_BY_SPESIALIZATION;
    }
}
