package com.springboot.demo.data;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    void deleteByEmail(String email);
    Iterable<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);
    Iterable<UserEntity> findByFirstName(String firstName);
    Iterable<UserEntity> findByLastName(String lastName);
}
