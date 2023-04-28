package com.springboot.demo.service;

import com.springboot.demo.shared.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDetails);
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);
    UserDto getUserByEmail(String email);
    boolean deleteUserByEmail(String email);

    List<UserDto> getUsersByFirstName(String firstName);
    List<UserDto> getUsersByLastName(String lastName);
    List<UserDto> getUsersByFirstNameAndLastName(String firstName, String lastName);
}
