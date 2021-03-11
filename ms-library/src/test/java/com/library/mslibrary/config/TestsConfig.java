package com.library.mslibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.library.mslibrary")
@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
public class TestsConfig {

}
