package com.telegram.bot.bik.api.parser;

import com.telegram.bot.bik.enums.AttributeEnum;
import com.telegram.bot.bik.enums.TagEnum;
import com.telegram.bot.bik.validation.Validable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrentScheduleGroup {

    public static final String COLOR_DATE_SCHEDULE = "#E0FFFF";

    private final ConnectionToSite connectionToSite;
    private final Validable dateValidation;

    public String findIdForCurrentGroup(String id, String name) {
        Document document = connectionToSite.connectToMainPage();
        Elements elementsByClassShadow = document.getElementsByClass("modernsmall");

        for (Element element : elementsByClassShadow) {
            String group = element.text();
            String[] fullNameGroup = group.split(" ");
            if (fullNameGroup[0].equals(id) && name.equals(fullNameGroup[1])) {
                String fullTagHref = element.attr(AttributeEnum.HREF.getName());
                String[] hrefWithTrash = fullTagHref.split("=");
                return hrefWithTrash[1];
            }
        }

        return "";
    }

    public String parseSchedule(String id, String name) {
        String idForCurrentGroup = findIdForCurrentGroup(id, name);
        Document document = connectionToSite.connectionToCurrentSchedule(idForCurrentGroup);
        Elements shadow = document.getElementsByTag(TagEnum.TR.getName());

        StringBuilder text = new StringBuilder();
        for (Element element : shadow) {
            String style = element.childNodes().get(0).attributes().get(TagEnum.BG_COLOR.getName());
            if (style.equals(COLOR_DATE_SCHEDULE)) {
                text.append('\n');
                text.append('\n');
                centerDateText(text);
                centerDateText(text);
                text.append("*");
                text.append(element.getElementsByTag(TagEnum.TD.getName()).text());
                text.append("*");
                centerDateText(text);
                centerDateText(text);

            }

            Elements shadow1 = element.getElementsByClass(TagEnum.SHADOW.getName());
            if (!shadow1.isEmpty()) {
                for (int i = 0; i < shadow1.size(); i++) {
                    Elements td = shadow1.get(i).getElementsByTag(TagEnum.TD.getName());
                    if (!td.isEmpty() && StringUtils.isNumeric(shadow1.get(0).getElementsByTag(TagEnum.TD.getName()).get(0).text())) {
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
