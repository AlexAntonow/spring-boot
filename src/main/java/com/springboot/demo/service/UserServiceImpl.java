package com.springboot.demo.service;

import com.springboot.demo.data.UserEntity;
import com.springboot.demo.data.UsersRepository;
import com.springboot.demo.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = new ArrayList<>();
        Iterable<UserEntity> users = usersRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        long count = StreamSupport.stream(users.spliterator(), false).count();
        if(count > 0){
            users.forEach(userEntity -> {
                UserDto userDto = modelMapper.map(userEntity, UserDto.class);
                allUsers.add(userDto);
            });
        }

        return allUsers;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        usersRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

}
