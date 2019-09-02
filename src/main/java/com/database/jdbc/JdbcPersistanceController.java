package com.database.jdbc;

import com.springinaction.datasources.UserRepository;
import com.springinaction.datasources.DataSources;
import com.springinaction.spittr.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/persistance")
public class JdbcPersistanceController {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcPersistanceController.class);

    @Autowired
    DataSources profileDataSource;

    //https://blog.csdn.net/Smile__1/article/details/88524503
    //这里如果写成JdbcUserRepository类型会出错
    @Autowired
    UserRepository jdbcUserRepository;

    @RequestMapping(value = "/jndi", method = RequestMethod.GET)
    public String jndiDataSource() {
        try {
            profileDataSource.jndiTestConnect();
            LOG.info("jndi connection test succeed");
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorpage";
        }
    }

    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String basicDriverManagerDataSource() {
        try {
            profileDataSource.driverManagerDataSourceTestConnect();
            LOG.info("driver manager data source connection test succeed");
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorpage";
        }
    }

    @RequestMapping(value = "embedded", method = RequestMethod.GET)
    public String embeddedDataSource() {
        try {
            profileDataSource.embeddedH2DataSourceTestConnect();
            LOG.info("embedded data source connection test succeed");
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
            return "errorpage";
        }
    }

    @RequestMapping(value = "/find/user", method = RequestMethod.GET)
    public String findByJdbcTemplate() {
        User user = jdbcUserRepository.findByUserName("test");
        LOG.info("test JdbcTemplate:" + user);
        return "home";
    }

    @RequestMapping(value = "/save/user", method = RequestMethod.GET)
    public String save() {
        User user = new User(2, "test2", "test2", "test2", "test2");
        jdbcUserRepository.save(user);
        return "home";
    }
}
