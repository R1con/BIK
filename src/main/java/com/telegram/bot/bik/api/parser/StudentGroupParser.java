package com.telegram.bot.bik.api.parser;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentGroupParser {
    private final ConnectionToSite connectionToSite;

    public List<String> parse(String nameGroup) {
        Document document = connectionToSite.connectToMainPage();
        Elements elementsByClassShadow = document.getElementsByClass("modernsmall");
        List<String> specialization = new ArrayList<>();
        elementsByClassShadow.forEach(element -> {
            String name = element.text();
            String[] fullNameGroup = name.split(" ");
            specialization.add(fullNameGroup[1]);
        });

        return specialization;
    }
}
