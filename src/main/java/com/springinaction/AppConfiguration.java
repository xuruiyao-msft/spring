package com.springinaction;

import com.database.hibernate.OrmConfiguration;
import com.database.jdbc.JdbcConfiguration;
import com.database.jpa.JpaConfiguration;
import com.database.springdatajpa.SpringDataJpaConfiguration;
import com.springinaction.spittr.Spittle;
import com.springinaction.spittr.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:app.properties")
//@ImportResource("Knight.xml")
@Import(value = {SpringDataJpaConfiguration.class, DataSourceConfiguration.class})
//@Import(value = {JpaConfiguration.class, DataSourceConfiguration.class})
@ComponentScan(basePackages = {"com.springinaction"}, excludeFilters = {@ComponentScan.Filter(value = EnableWebMvc.class)})
public class AppConfiguration {

    @Autowired
    Environment environment;

    //解析属性占位符需要这个bean
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //前端配置s:message需要这个bean解析message.properties中的key-value
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @Bean
    public SpittleRepository getSpittleRepository() {
        return new SpittleRepository() {
            @Override
            public List<Spittle> findSpittles(long max, int count) {
                List<Spittle> spittleList = new ArrayList<Spittle>();
                for (int i = 0; i < count; i++) {
                    spittleList.add(new Spittle(Long.valueOf(i), "this is the " + i));
                }
                return spittleList;
            }

            @Override
            public Spittle findOne(long spittleOne) {
                return new Spittle(spittleOne, "random one");
            }

            @Override
            public Spittle findByUserName(String userName) {
                return new Spittle(1l, userName);
            }
        };
    }

    //用于将特定类型的持久化层，例如hibernate和jpa转换为spring统一的异常体系，这样异常处理可以跟持久化层解耦
    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


}
