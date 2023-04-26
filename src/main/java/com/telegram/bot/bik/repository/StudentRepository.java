package com.telegram.bot.bik.repository;

import com.telegram.bot.bik.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByTelegramId(Long telegramId);
}
