package com.springboot.demo.shared;


import com.springboot.demo.data.UserEntity;
import com.springboot.demo.exception.NotFoundException;
import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


@Component
public class Utils {

    public List<UserResponseModel> getUserResponseModelList(List<UserDto> users) throws NotFoundException {

        if (users == null || users.isEmpty()) {
            throw new NotFoundException("0 records found");
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserResponseModel> usersList = new ArrayList<>();
        users.forEach((userDto -> {
            UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
            usersList.add(userResponseModel);
        }));


        return usersList;
    }

    public UserResponseModel getUserResponseModel(UserDto userDto) throws NotFoundException {
        if (userDto == null) {
            throw new NotFoundException("User not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(userDto, UserResponseModel.class);
    }

    public UserDto getUserDto(UserRequestModel userDetails) throws NotFoundException {
        if(userDetails == null){
            throw new NotFoundException("Request object is null");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userDetails, UserDto.class);
    }

    public List<UserDto> getUserDtoList(Iterable<UserEntity> users){
        List<UserDto> allUsers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        long count = StreamSupport.stream(users.spliterator(), false).count();
        if(count > 0){
            users.forEach(userEntity -> {
                UserDto userDto = modelMapper.map(userEntity, UserDto.class);
                allUsers.add(userDto);
            });
        }else{
            throw new ValidationException("List of entities is null or empty");
        }

        return allUsers;
    }

}
