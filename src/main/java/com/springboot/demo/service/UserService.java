package com.springboot.demo.service;

import com.springboot.demo.shared.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDetails);
    List<UserDto> getAllUsers();
}
