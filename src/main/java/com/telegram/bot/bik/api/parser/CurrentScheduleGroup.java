package com.telegram.bot.bik.api.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrentScheduleGroup {
    private final ConnectionToSite connectionToSite;

    public int findIdForCurrentGroup(String id, String name) {
        Document document = connectionToSite.connectToMainPage();
        Elements elementsByClassShadow = document.getElementsByClass("modernsmall");

        for (Element element : elementsByClassShadow) {
            String group = element.text();
            String[] fullNameGroup = group.split(" ");
            if (fullNameGroup[0].equals(id) && name.equals(fullNameGroup[1])) {
                String fullTagHref = element.attr("href"); // аттрибут href у тега a
                String[] hrefWithTrash = fullTagHref.split("=");
                return Integer.parseInt(hrefWithTrash[1]);
            }
        }

        return 0;
    }

    public void parseSchedule(String id, String name) {
        int idForCurrentGroup = findIdForCurrentGroup(id, name);
        connectionToSite.connectionToCurrentSchedule(idForCurrentGroup);

    }
}
