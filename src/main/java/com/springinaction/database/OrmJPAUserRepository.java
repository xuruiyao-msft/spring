package com.springinaction.database;


import com.springinaction.spittr.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrmJPAUserRepository implements UserRepository{

    //使用该注解不会真的注入一个entityManager实例，而是注入一个代理，每次请求都会创建一个entityManager，保证线程安全
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return 0;
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User findOne(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUserName(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

}
