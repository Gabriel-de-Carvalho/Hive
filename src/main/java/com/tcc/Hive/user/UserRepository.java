package com.tcc.Hive.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, Integer> {

    public User findByUser(String name);
    public User findByEmail(String email);

}
