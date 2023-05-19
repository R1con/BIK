package com.telegram.bot.bik.api.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParserGroup {
    public static final String MODERNSMALL = "modernsmall";
    private final ConnectionToSite connectionToSite;

    public List<String> parse(String group) {
        Document document = connectionToSite.connectToMainPage();

        Elements elementsByClassShadow = document.getElementsByClass(MODERNSMALL);
        List<String> groupsByCurrentName = new ArrayList<>();
        for (Element element : elementsByClassShadow) {
            String name = element.text();
            String[] fullNameGroup = name.split(" ");
            String nameGroup = fullNameGroup[1];
            String idGroup = fullNameGroup[0];
            if (group.equals(nameGroup)) {
                groupsByCurrentName.add(idGroup);
            }
        }

        return groupsByCurrentName;
    }
}
