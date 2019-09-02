package com.springinaction;

import com.alibaba.druid.pool.DruidDataSource;
import com.springinaction.datasources.MyDataSourceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Autowired
    MyDataSourceConfiguration myDataSource;

    @Bean(value = "druidDataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(myDataSource.getUsername());
        dataSource.setPassword(myDataSource.getPassword());
        dataSource.setUrl(myDataSource.getUrl());
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(60000);
        dataSource.setDriverClassName(myDataSource.getDriverClass());
        dataSource.setValidationQuery(myDataSource.getValidationQuery());
        return dataSource;
    }

    //    使用jndi的方式创建数据库数据源，该种方式可以通过name引用数据源,
//    在tomcat的lib目录下放入mysql的包
//    在tomcat的conf目录下配置
//    <Resource name="jdbc/spittr"
//    auth="Container"
//    type="javax.sql.DataSource"
//    driverClassName="com.mysql.jdbc.Driver"
//    url="jdbc:mysql://localhost:3306/springaction"
//    username="root" password="AHEGONGZHU"
//    maxActive="20" maxIdle="10"
//    maxWait="10000"/>
    @Bean(value = "profileDataSource")
    @Profile("jndi")
    public DataSource dataSource() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/spittr");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
        jndiObjectFactoryBean.afterPropertiesSet();
        return (DataSource) jndiObjectFactoryBean.getObject();
    }

    @Bean(value = "profileDataSource")
    @Profile("embedded")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    //该数据源是基于JDBC的数据源，它相对于druid区别是没有池化管理，性能比较差
    @Bean(value = "profileDataSource")
    @Profile("driver")
    public DataSource driverManagerDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(myDataSource.getDriverClass());
        ds.setUrl(myDataSource.getUrl());
        ds.setUsername(myDataSource.getUsername());
        ds.setPassword(myDataSource.getPassword());
        return ds;
    }

}
