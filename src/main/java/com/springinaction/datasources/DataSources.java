package com.springinaction.datasources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;


@Component
public class DataSources {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSources.class);

    @Autowired
    DataSource profileDataSource;

    public void jndiTestConnect() {
        //通过name就能引用到数据源
//        DataSource ds = (DataSource) jndiObjectFactoryBean.getJndiTemplate().lookup("java:comp/env/jdbc/spittr");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(profileDataSource);
        jdbcTemplate.execute("select 1 from DUAL");
        LOGGER.info(profileDataSource.getClass().getName());
    }

    public void driverManagerDataSourceTestConnect() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(profileDataSource);
        jdbcTemplate.execute("select 1 from DUAL");
        LOGGER.info(profileDataSource.getClass().getName());
    }

    public void embeddedH2DataSourceTestConnect() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(profileDataSource);
        jdbcTemplate.execute("select 1 from sec_user");
        LOGGER.info(profileDataSource.getClass().getName());
    }
}
