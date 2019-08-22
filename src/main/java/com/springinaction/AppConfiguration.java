package com.springinaction;

import com.springinaction.spittr.Spittle;
import com.springinaction.spittr.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:app.properties")
//@ImportResource("Knight.xml")
@ComponentScan(basePackages = {"com.springinaction"}, excludeFilters = {@ComponentScan.Filter(value = EnableWebMvc.class)})
public class AppConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource(){
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

}
