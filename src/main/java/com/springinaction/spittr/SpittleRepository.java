package com.springinaction.spittr;

import java.util.List;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);

    Spittle findOne(long spittleId);

    Spittle findByUserName(String userName);
}
