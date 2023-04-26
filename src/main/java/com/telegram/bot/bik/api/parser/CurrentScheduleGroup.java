package com.telegram.bot.bik.api.parser;

import com.telegram.bot.bik.validation.DateValidation;
import com.telegram.bot.bik.validation.Validable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrentScheduleGroup {
    private final ConnectionToSite connectionToSite;
    private final Validable dateValidation;

    public String findIdForCurrentGroup(String id, String name) {
        Document document = connectionToSite.connectToMainPage();
        Elements elementsByClassShadow = document.getElementsByClass("modernsmall");

        for (Element element : elementsByClassShadow) {
            String group = element.text();
            String[] fullNameGroup = group.split(" ");
            if (fullNameGroup[0].equals(id) && name.equals(fullNameGroup[1])) {
                String fullTagHref = element.attr("href"); // аттрибут href у тега a
                String[] hrefWithTrash = fullTagHref.split("=");
                return hrefWithTrash[1];
            }
        }

        return "";
    }

    public String parseSchedule(String id, String name) {
        String idForCurrentGroup = findIdForCurrentGroup(id, name);
        Document document = connectionToSite.connectionToCurrentSchedule(idForCurrentGroup);
        Elements shadow = document.getElementsByTag("tr");

        StringBuilder text = new StringBuilder();
        for (Element element : shadow) {
            String style = element.childNodes().get(0).attributes().get("bgcolor");
            if (style.equals("#E0FFFF")) {
                text.append('\n');
                text.append('\n');
                centerDateText(text);
                centerDateText(text);
                text.append("*");
                text.append(element.getElementsByTag("td").text());
                text.append("*");
                centerDateText(text);
                centerDateText(text);

            }

            Elements shadow1 = element.getElementsByClass("shadow");
            if (!shadow1.isEmpty()) {
                for (int i = 0; i < shadow1.size(); i++) {
                    Elements td = shadow1.get(i).getElementsByTag("td");
                    if (!td.isEmpty() && StringUtils.isNumeric(shadow1.get(0).getElementsByTag("td").get(0).text())) {
                        for (Element element1 : shadow1) {
                            if (dateValidation.validate()) {
                                text.append('\n');
                            }
                            if (Character.isDigit(element1.text().charAt(0)) && element1.text().charAt(1) == 32) {
                                text.append("_");
                                text.append(element1.text().replace(" ", "  "));
                                text.append("_");
                            }
                        }
                    }
                }
            }
        }

        return text.toString();
    }

    private void centerDateText(StringBuilder text) {
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
        text.append('\t');
    }
}
