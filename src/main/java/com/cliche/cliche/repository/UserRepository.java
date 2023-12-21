package com.cliche.cliche.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cliche.cliche.domain.user.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailLike(String email);
}