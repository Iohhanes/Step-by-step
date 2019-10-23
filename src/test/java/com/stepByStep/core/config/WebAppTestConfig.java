package com.stepByStep.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan()
@Import({AppConfig.class,PersistenceConfig.class})
public class WebAppTestConfig {
}
