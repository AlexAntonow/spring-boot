package com.springboot.demo.service;

import com.springboot.demo.data.UserEntity;
import com.springboot.demo.data.UsersRepository;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;
    Utils utils;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, Utils utils){
        this.usersRepository = usersRepository;
        this.utils = utils;
    }


    @Override
    public List<UserDto> getAllUsers() {
        Iterable<UserEntity> users = usersRepository.findAll();

        return utils.getUserDtoList(users);
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
        Iterable<UserEntity> users = usersRepository.findByFirstName(firstName);

        return utils.getUserDtoList(users);
    }

    @Override
    public List<UserDto> getUsersByLastName(String lastName) {
        Iterable<UserEntity> users = usersRepository.findByLastName(lastName);

        return utils.getUserDtoList(users);
    }

    @Override
    public List<UserDto> getUsersByFirstNameAndLastName(String firstName, String lastName) {
        Iterable<UserEntity> users = usersRepository.findByFirstNameAndLastName(firstName, lastName);

        return utils.getUserDtoList(users);
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
