package com.springinaction.test3;

import com.springinaction.AppConfiguration;
import com.springinaction.knights.AppProperty;
import com.springinaction.knights.Knight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
//@ContextConfiguration(locations = {"classpath:Knight.xml"})
public class SpringTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testImportResource() {
        Knight knight = applicationContext.getBean(Knight.class);
        knight.embarkOnQuest();
       AppProperty appProperty = applicationContext.getBean(AppProperty.class);
        System.out.println(appProperty.getAbs());
    }
}
