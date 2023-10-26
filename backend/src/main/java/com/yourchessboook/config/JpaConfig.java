package com.yourchessboook.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = { "de.yourchessboook.model" })
@EnableJpaRepositories(basePackages = { "de.yourchessboook.repo" })
@EnableTransactionManagement
public class JpaConfig {
}
