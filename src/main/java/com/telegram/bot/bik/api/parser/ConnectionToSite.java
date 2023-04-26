package com.telegram.bot.bik.api.parser;

import com.telegram.bot.bik.config.properties.SiteProperties;
import com.telegram.bot.bik.exception.ConnectionSiteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConnectionToSite {
    private final SiteProperties siteProperties;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public Document connectToMainPage() {
        Document document;
        try {
            document = Jsoup.connect(siteProperties.getSchedule())
//                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .get();
            return document;
        } catch (IOException e) {
            log.error("error with connection to site: " + e.getMessage());
            throw new ConnectionSiteException("Not connect to site: " + e);
        }
    }

    public Document connectionToCurrentSchedule(String id) {
        Document document;
        try {
            document = Jsoup.connect(siteProperties.getGroup() + id)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .get();
            return document;
        } catch (IOException e) {
            log.error("error with connection to current schedule: " + e.getMessage());
            throw new ConnectionSiteException("Not connect to current schedule: " + e);
        }
    }
}
