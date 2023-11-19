package com.telegram.bot.bik.service.interfaces;

import com.telegram.bot.bik.model.entity.Group;

import java.util.List;

public interface GroupService {

    void save(String name);
    void saveAll(List<String> names);

    List<Group> findAll();
}
