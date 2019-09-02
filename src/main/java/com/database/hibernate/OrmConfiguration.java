package com.database.hibernate;


import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.database.hibernate"})
public class OrmConfiguration {

    //这里注入druidDataSource
    //hibernate持久化层的session factory bean
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource druidDataSource) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(druidDataSource);
        sfb.setPackagesToScan(new String[]{"com.springinaction.spittr"});
        Properties properties = new Properties();
        properties.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
        sfb.setHibernateProperties(properties);
        return sfb;
    }

    //除了配置sessionFactory和persistenceTranslation，在使用repository的时候，
    // 还要配置tx manager，在java root config上加上注解@EnableTransactionManagement，以及@Transactional在repository上
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
