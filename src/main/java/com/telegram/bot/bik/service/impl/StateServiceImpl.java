package com.telegram.bot.bik.service.impl;

import com.telegram.bot.bik.enums.StateEnum;
import com.telegram.bot.bik.model.entity.State;
import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.repository.StateRepository;
import com.telegram.bot.bik.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    @Override
    @Transactional
    public void save(Long telegramId, StateEnum stateName) {
        stateRepository.findStateByStudentTelegramId(telegramId)
                .ifPresentOrElse(state -> saveStateIfPresentUser(state.getStudent().getTelegramId(), stateName),
                        () -> saveNewState(telegramId, stateName));
    }

    @Override
    public State get(Long telegramId) {
        return stateRepository.findStateByStudentTelegramId(telegramId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteByTelegramId(Long telegramId) {
        stateRepository.deleteStateByStudentTelegramId(telegramId);
    }

    public void saveStateIfPresentUser(Long telegramId, StateEnum state) {
        State stateUser = stateRepository.findStateByStudentTelegramId(telegramId).orElseThrow();
        stateUser.setName(state);
        stateRepository.save(stateUser);
    }

    public void saveNewState(Long telegramId, StateEnum name) {
        Student student = new Student();
        student.setTelegramId(telegramId);

        State state = new State();
        state.setName(name);
        state.setStudent(student);

        stateRepository.save(state);
    }
}
