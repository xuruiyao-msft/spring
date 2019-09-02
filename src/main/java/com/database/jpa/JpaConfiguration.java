package com.database.jpa;


import com.database.JPACommonConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.database.jpa"})
public class JpaConfiguration extends JPACommonConfig {
}
