package com.telegram.bot.bik.config.properties;

import com.telegram.bot.bik.config.factory.YamlPropertySourceFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Setter
@Getter
@PropertySource(value = "classpath:messages.yaml", factory = YamlPropertySourceFactory.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "message")
public class MessageProperties {
    String startTitle;
    String myGroup;
    String otherGroup;
    String menu;
    String selectedGroup;
    String chooseCourse;
    String chooseGroup;
}
