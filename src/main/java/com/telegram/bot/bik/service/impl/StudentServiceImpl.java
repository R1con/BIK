package com.telegram.bot.bik.service.impl;

import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {
    private final StudentRepository studentRepository;

    public void save(Long telegramId) {
        Student student = new Student();
        student.setTelegramId(telegramId);
        studentRepository.save(student);
    }
}
