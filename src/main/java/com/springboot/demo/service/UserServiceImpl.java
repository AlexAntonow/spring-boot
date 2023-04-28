package com.springboot.demo.service;

import com.springboot.demo.data.UserEntity;
import com.springboot.demo.data.UsersRepository;
import com.springboot.demo.shared.UserDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
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
        System.out.println(count);
        if(count > 0){
            users.forEach(userEntity -> {
                UserDto userDto = modelMapper.map(userEntity, UserDto.class);
                allUsers.add(userDto);
            });
        }

        return allUsers;
    }

    @Override
    public UserDto getUserById(long id){
        Optional<UserEntity> userEntity = usersRepository.findById(id);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null){
            return null;
        }else{
            return new ModelMapper().map(userEntity, UserDto.class);
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        try{
            usersRepository.deleteByEmail(email);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    @Override
    public List<UserDto> getUsersByFirstName(String firstName) {
        List<UserDto> allUsers = new ArrayList<>();
        Iterable<UserEntity> users = usersRepository.findByFirstName(firstName);
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
    public List<UserDto> getUsersByLastName(String lastName) {
        List<UserDto> allUsers = new ArrayList<>();
        Iterable<UserEntity> users = usersRepository.findByLastName(lastName);
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
    public List<UserDto> getUsersByFirstNameAndLastName(String firstName, String lastName) {
        List<UserDto> allUsers = new ArrayList<>();
        Iterable<UserEntity> users = usersRepository.findByFirstNameAndLastName(firstName, lastName);
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
