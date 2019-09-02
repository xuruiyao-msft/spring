package com.springinaction.database;

import com.springinaction.spittr.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class OrmHibernateUserRepository implements UserRepository{

    private SessionFactory sessionFactory;

    @Autowired
    public OrmHibernateUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public long count() {
        return findAll().size();
    }


    public User save(User user) {
        Serializable id = currentSession().save(user);
        return new User((int) id, user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
    }

    public User findOne(long id) {
        return (User) currentSession().get(User.class, id);
    }

    public User findByUserName(String username) {
        return (User) currentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .list().get(0);
    }

    public List<User> findAll() {
        return currentSession().createCriteria(User.class).list();
    }
}
