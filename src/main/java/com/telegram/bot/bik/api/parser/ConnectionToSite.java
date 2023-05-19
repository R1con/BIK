package com.telegram.bot.bik.api.parser;

import com.telegram.bot.bik.config.properties.SiteProperties;
import com.telegram.bot.bik.exception.ConnectionSiteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConnectionToSite {
    private final SiteProperties siteProperties;

    public Document connectToMainPage() {
        Document document;
        try {
            document = Jsoup.connect(siteProperties.getSchedule())
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
                    .ignoreContentType(true)
                    .get();
            return document;
        } catch (IOException e) {
            log.error("error with connection to current schedule: " + e.getMessage());
            throw new ConnectionSiteException("Not connect to current schedule: " + e);
        }
    }
}
