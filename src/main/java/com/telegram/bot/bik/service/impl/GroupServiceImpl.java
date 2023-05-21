package com.telegram.bot.bik.service.impl;

import com.telegram.bot.bik.model.entity.Group;
import com.telegram.bot.bik.repository.GroupRepository;
import com.telegram.bot.bik.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public void save(String name) {
        if (!groupRepository.existsByName(name)) {
            Group group = new Group();
            group.setName(name);
            groupRepository.save(group);
        }
    }

    @Override
    @Transactional
    public void saveAll(List<String> names) {
        for (String name : names) {
            save(name);
        }
    }
}
