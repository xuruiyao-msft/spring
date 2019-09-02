package com.springinaction.test3;

import com.alibaba.druid.pool.DruidDataSource;
import com.springinaction.AppConfiguration;
import com.springinaction.knights.AppProperty;
import com.springinaction.knights.Knight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;


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

    @Test
    public void testDataSource() {
        DruidDataSource dataSource = applicationContext.getBean(DruidDataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("select 1 from DUAL");
    }

    @Test
    public void testJndiDataSource() throws NamingException {
//        该方法必须在tomcat中才能运行，所以这个地方不能测试
//        JndiObjectFactoryBean jndiObjectFactoryBean = applicationContext.getBean(JndiObjectFactoryBean.class);
//
//        DataSource ds = (DataSource)jndiObjectFactoryBean.getJndiTemplate().lookup("java:comp/jdbc/spittr");
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
//        jdbcTemplate.execute("select 1 from DUAL");
    }
}
