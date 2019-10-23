package com.stepByStep.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.stepByStep.core.service","com.stepByStep.core.util"})
public class AppConfig {
}
