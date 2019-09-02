package com.database.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.database.jdbc"})
public class JdbcConfiguration {

    @Bean
    //通过profileDataSource可以区分不同的datasource,参考https://blog.csdn.net/Mr_lyh/article/details/94023266
    public JdbcOperations jdbcOperations(DataSource profileDataSource) {
        return new JdbcTemplate(profileDataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }
}
