package com.springboot.demo.controller;


import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import com.springboot.demo.service.UserService;
import com.springboot.demo.shared.UserDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponseModel>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserResponseModel> usersList = new ArrayList<>();
        users.forEach((userDto -> {
            UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
            usersList.add(userResponseModel);
        }));

        if(usersList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable Integer id){
        UserDto userDto = userService.getUserById(id);
        if(userDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserResponseModel returnValue = modelMapper.map(userDto, UserResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseModel> getUserByEmail(@PathVariable String email){
        UserDto userDto = userService.getUserByEmail(email);

        if(userDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserResponseModel returnValue = modelMapper.map(userDto, UserResponseModel.class);
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseModel>> searchUsers(
            @RequestParam(value = "first-name", required = false) String firstName,
            @RequestParam(value = "last-name", required = false) String lastName){
        List<UserDto> users = null;
        if(firstName != null && lastName != null){
            users = userService.getUsersByFirstNameAndLastName(firstName, lastName);
        }else if(lastName != null){
            users = userService.getUsersByLastName(lastName);
        }else if(firstName != null){
            users = userService.getUsersByFirstName(firstName);
        }

        if(users == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<UserResponseModel> usersList = new ArrayList<>();

        users.forEach(userDto -> {
            UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
            usersList.add(userResponseModel);
        });

        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UserRequestModel userDetails){
        String email = userDetails.getEmail();
        if(userService.getUserByEmail(email) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Getting existing user
        UserDto existingUser = userService.getUserByEmail(email);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userDto.setId(existingUser.getId());

        UserDto createdValue = userService.createUser(userDto);

        UserResponseModel returnValue = modelMapper.map(createdValue, UserResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userDetails){
        String email = userDetails.getEmail();
        if(userService.getUserByEmail(email) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdValue = userService.createUser(userDto);
        UserResponseModel returnValue = modelMapper.map(createdValue, UserResponseModel.class);

        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    ResponseEntity<Void> delete (@PathVariable String email){
        if(userService.getUserByEmail(email) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean res = userService.deleteUserByEmail(email);
        if(res){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
