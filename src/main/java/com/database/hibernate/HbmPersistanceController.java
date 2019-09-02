package com.database.hibernate;

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
public class HbmPersistanceController {

    private static final Logger LOG = LoggerFactory.getLogger(HbmPersistanceController.class);

    @Autowired
    UserRepository ormHibernateUserRepository;


    @RequestMapping(value = "/orm/hbm", method = RequestMethod.GET)
    public String findUserByHibernalte() {
        User user = ormHibernateUserRepository.findOne(1);
        LOG.info("find user: " + user);
        return "home";
    }
}
