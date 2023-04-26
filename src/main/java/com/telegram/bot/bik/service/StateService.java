package com.telegram.bot.bik.service;

import com.telegram.bot.bik.enums.StateEnum;
import com.telegram.bot.bik.model.entity.State;

public interface StateService {
    void save(Long telegramId, StateEnum state);
    State get(Long telegramId);
    void deleteByTelegramId(Long telegramId);
}
