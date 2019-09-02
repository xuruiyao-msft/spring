package com.springinaction;

import com.alibaba.druid.pool.DruidDataSource;
import com.springinaction.database.MyDataSourceConfiguration;
import com.springinaction.spittr.Spittle;
import com.springinaction.spittr.SpittleRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@PropertySource("classpath:app.properties")
@EnableTransactionManagement
//@ImportResource("Knight.xml")
@EnableJpaRepositories(basePackages = "com.springinaction.database")
@ComponentScan(basePackages = {"com.springinaction"}, excludeFilters = {@ComponentScan.Filter(value = EnableWebMvc.class)})
public class AppConfiguration {

    @Autowired
    Environment environment;

    @Autowired
    MyDataSourceConfiguration myDataSource;

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

    @Bean
    //通过profileDataSource可以区分不同的datasource,参考https://blog.csdn.net/Mr_lyh/article/details/94023266
    public JdbcOperations jdbcOperations(DataSource profileDataSource) {
        return new JdbcTemplate(profileDataSource);
    }

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

    //用于将特定类型的持久化层，例如hibernate和jpa转换为spring统一的异常体系，这样异常处理可以跟持久化层解耦
    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    //除了配置sessionFactory和persistenceTranslation，在使用repository的时候，
    // 还要配置tx manager，在java root config上加上注解@EnableTransactionManagement，以及@Transactional在repository上
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return jpaVendorAdapter;
    }

    //基于hibernate jpa构建持久化层
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource druidDataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(druidDataSource);
        emfb.setPackagesToScan("com.springinaction.spittr");
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        return emfb;
    }

}
