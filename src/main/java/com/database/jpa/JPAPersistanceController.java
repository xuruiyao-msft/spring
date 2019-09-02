package com.database.jpa;

import com.springinaction.datasources.UserRepository;
import com.springinaction.spittr.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/persistance")
public class JPAPersistanceController {

    private static final Logger LOG = LoggerFactory.getLogger(JPAPersistanceController.class);

    @Autowired
    UserRepository ormJPAUserRepository;

    @RequestMapping(value = "/orm/jpa", method = RequestMethod.GET)
    public String findUserByJPA() {
        User user = ormJPAUserRepository.findOne(1);
        LOG.info("find user: " + user);
        return "home";
    }
}
