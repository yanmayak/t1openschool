package ru.yanmayak.t1openschool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.yanmayak.aspect.LoggingAspect;
import ru.yanmayak.aspect.LoggingConfig;
import ru.yanmayak.aspect.LoggingLevel;

@Configuration
@Component
public class LogConfig {
    public LoggingLevel level;

    public LogConfig(@Value("${logging.aspect.level}") String level) {
        this.level = LoggingLevel.valueOf(level);
    }

    @Bean
    public LoggingConfig loggingConfig() {
        return new LoggingConfig(level);
    }

    @Bean
    public LoggingAspect loggingAspect(LoggingConfig loggingConfig) {
        return new LoggingAspect(loggingConfig);
    }
}