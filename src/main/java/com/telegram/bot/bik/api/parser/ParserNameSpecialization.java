package com.telegram.bot.bik.api.parser;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@EqualsAndHashCode
@Slf4j
public class ParserNameSpecialization {
    private final ConnectionToSite connectionToSite;

    public Set<String> parseSpecialization() {
        Document document = connectionToSite.connectToMainPage();

        Elements elementsByClassShadow = document.getElementsByClass("modernsmall");
        Set<String> specialization = new HashSet<>();
        elementsByClassShadow.forEach(element -> {
            String name = element.text();
            String[] fullNameGroup = name.split(" ");
            specialization.add(fullNameGroup[1]);
        });


        // TODO : алгоритм подойдет для конкретной группы, немного перепиисать и заебись
//        for (Element element : elementsByClassShadow) {
//            String href1 = element.attr("href"); // аттрибут href у тега a
//            String[] split = href1.split("=");
//            String text = element.text();
//            nameSpecializationNormal.put(split[1], text);
//        }


        return specialization;
    }

}
