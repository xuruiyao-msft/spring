package com.springinaction.database;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MyDataSourceConfiguration {

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.driverclass}")
    private String driverClass;

    @Value("${datasource.validationQuery}")
    private String validationQuery;
}
