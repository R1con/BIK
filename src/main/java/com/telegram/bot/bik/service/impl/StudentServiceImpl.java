package com.telegram.bot.bik.service.impl;

import com.telegram.bot.bik.model.entity.Student;
import com.telegram.bot.bik.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {
    private final StudentRepository studentRepository;

    public void save(Long telegramId) {
        Student student = new Student();
        student.setTelegramId(telegramId);
        studentRepository.save(student);
    }

    public void save(Long telegramId, String groupName) {
        Optional<Student> studentByTelegramId = studentRepository.findStudentByTelegramId(telegramId);
        if (studentByTelegramId.isEmpty()) {
            Student student = new Student();
            student.setTelegramId(telegramId);
            student.setGroupName(groupName);
            studentRepository.save(student);
        } else {
            Student student = studentByTelegramId.get();
            student.setGroupName(groupName);
            studentRepository.save(student);
        }

    }
}
