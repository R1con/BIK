package com.telegram.bot.bik.repository;

import com.telegram.bot.bik.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findStateByStudentTelegramId(Long telegramId);
    void deleteStateByStudentTelegramId(Long telegramId);
}
