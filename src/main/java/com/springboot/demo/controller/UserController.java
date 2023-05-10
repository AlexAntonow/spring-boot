package com.springboot.demo.controller;


import com.springboot.demo.exception.NotFoundException;
import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import com.springboot.demo.service.UserService;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Utils utils;

    @GetMapping()
    public ResponseEntity<List<UserResponseModel>> getAllUsers() throws NotFoundException {
        List<UserDto> users = userService.getAllUsers();

        return new ResponseEntity<>(utils.getUserResponseModelList(users), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable Integer id) throws NotFoundException {
        UserDto userDto = userService.getUserById(id);

        return new ResponseEntity<>(utils.getUserResponseModel(userDto), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseModel> getUserByEmail(@PathVariable String email) throws NotFoundException {
        UserDto userDto = userService.getUserByEmail(email);

        return new ResponseEntity<>(utils.getUserResponseModel(userDto), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseModel>> searchUsers(
            @RequestParam(value = "first-name", required = false) String firstName,
            @RequestParam(value = "last-name", required = false) String lastName) throws NotFoundException {
        List<UserDto> users = null;
        if(firstName != null && lastName != null){
            users = userService.getUsersByFirstNameAndLastName(firstName, lastName);
        }else if(lastName != null){
            users = userService.getUsersByLastName(lastName);
        }else if(firstName != null){
            users = userService.getUsersByFirstName(firstName);
        }

        return new ResponseEntity<>(utils.getUserResponseModelList(users), HttpStatus.OK);
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UserRequestModel userDetails) throws NotFoundException {
        String email = userDetails.getEmail();
        if(userService.getUserByEmail(email) == null){
            throw new ValidationException("Failed to update, user not found");
        }

        //Getting existing user
        UserDto existingUser = userService.getUserByEmail(email);

        UserDto userDto = utils.getUserDto(userDetails);
        userDto.setId(existingUser.getId());

        UserDto createdValue = userService.createUser(userDto);

        return new ResponseEntity<>(utils.getUserResponseModel(createdValue), HttpStatus.OK);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userDetails) throws NotFoundException {
        String email = userDetails.getEmail();
        if(userService.getUserByEmail(email) != null){
            throw new ValidationException("User with provided email already exists");
        }

        UserDto userDto = utils.getUserDto(userDetails);

        UserDto createdValue = userService.createUser(userDto);

        return new ResponseEntity<>(utils.getUserResponseModel(createdValue), HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete (@PathVariable String email){
        if(userService.getUserByEmail(email) == null){
            throw new ValidationException("Failed to delete, user not found");
        }
        boolean res = userService.deleteUserByEmail(email);
        if(res){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ValidationException("An error occurred while executing request");
    }

}
