package com.database.springdatajpa;

import com.springinaction.spittr.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/persistance")
public class SpringDataJpaPersistanceController {

    private static final Logger LOG = LoggerFactory.getLogger(SpringDataJpaPersistanceController.class);

    @Autowired
    SpringDataJpaRepository springDataJpaRepository;

    @RequestMapping(value = "/orm/data", method = RequestMethod.GET)
    public String findUserBySpringDataJPA() {
        User user = springDataJpaRepository.findByIdOrderByFirstNameAsc(1l);
        LOG.info("find user: " + user);
        return "home";
    }
}
