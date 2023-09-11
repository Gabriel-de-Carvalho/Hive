package com.tcc.Hive.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository  extends MongoRepository<UserHive, Integer> {

    public UserHive findByUser(String name);
    public UserHive findByEmail(String email);

    public List<UserHive> findAllByEmailIn(List<String> emails);

}
