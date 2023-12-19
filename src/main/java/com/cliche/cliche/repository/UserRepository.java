package com.cliche.cliche.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cliche.cliche.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailLike(String email);
}