package com.database.jdbc;

import com.springinaction.datasources.UserRepository;
import com.springinaction.spittr.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//注解@Repository表明该类会被扫描成一个bean
//这里不加@Transactional也是可以的，奇怪
@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT_USER = "insert into user(first_name, last_name, user_name, password) values(?,?,?,?)";
    private static final String FIND_USER_BY_NAME = "select first_name, last_name, user_name, password from user where user_name=?";


    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcUserRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long count() {
        //TODO
        return 0;
    }

    @Override
    public User save(User user) {
        jdbcOperations.update(INSERT_USER, user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
        return user;
    }

    @Override
    public User findOne(long id) {
        //TODO
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return jdbcOperations.queryForObject(FIND_USER_BY_NAME, new UserMapper(), username);
    }

    @Override
    public List<User> findAll() {
        //TODO
        return null;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(1, rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_name"),
                    rs.getString("password"));
        }
    }
}
