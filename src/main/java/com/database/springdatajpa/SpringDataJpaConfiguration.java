package com.database.springdatajpa;

import com.database.JPACommonConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.database.springdatajpa")
@ComponentScan(basePackages = {"com.database.springdatajpa"})
public class SpringDataJpaConfiguration extends JPACommonConfig {

}
