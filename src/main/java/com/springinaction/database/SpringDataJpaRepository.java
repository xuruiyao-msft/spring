package com.springinaction.database;

import com.springinaction.spittr.User;
import org.springframework.data.repository.Repository;

public interface SpringDataJpaRepository extends Repository<User,Long> {
    User findByIdOOrderByFirstName(Long id);
}
