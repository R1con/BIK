package com.telegram.bot.bik.service.interfaces;

import java.util.List;

public interface GroupService {

    void save(String name);
    void saveAll(List<String> names);
}
