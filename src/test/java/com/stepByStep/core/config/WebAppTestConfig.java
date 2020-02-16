package com.stepByStep.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppConfig.class, PersistenceTestConfig.class})
public class WebAppTestConfig {
}
