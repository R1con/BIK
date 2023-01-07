package com.telegram.bot.bik.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bik")
public class SiteProperties {
    private String schedule;
    private String group;

}
