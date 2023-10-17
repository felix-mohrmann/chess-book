package com.yourchessboook.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = { "com.yourchessboook.model" })
@EnableJpaRepositories(basePackages = { "com.yourchessboook.repo" })
@EnableTransactionManagement
public class JpaConfig {
}
