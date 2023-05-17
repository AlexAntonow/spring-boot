package com.springboot.demo;


import com.springboot.demo.data.UserEntity;
import com.springboot.demo.data.UsersRepository;
import com.springboot.demo.service.UserServiceImpl;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    Utils utils;

    @Test
    public void getAllUsersTest(){
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name1", "LastName1",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserEntity userEntity1 = new UserEntity(2, "First Name1", "LastName1",
                "mail1@mail.com", 38);

        Iterable<UserEntity> userEntities = Arrays.asList(userEntity, userEntity1);

        when(usersRepository.findAll()).thenReturn(userEntities);
        when(userService.getAllUsers()).thenReturn(userDtoList);
        when(utils.getUserDtoList(userEntities)).thenReturn(userDtoList);

        List<UserDto> userDtos = userService.getAllUsers();

        assertThat(userDtos.get(0).getFirstName()).isEqualTo(userDto1.getFirstName());
        assertThat(userDtos.get(0).getEmail()).isEqualTo(userDto1.getEmail());
        assertThat(userDtos.get(0).getLastName()).isEqualTo(userDto1.getLastName());
        assertThat(userDtos.get(0).getAge()).isEqualTo(userDto1.getAge());

    }

    @Test
    public void getUserByEmailTest(){
        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);
        String email = "mail@mail.com";

        when(usersRepository.findByEmail(email)).thenReturn(userEntity);

        UserDto userDto = userService.getUserByEmail(email);

        assertThat(userDto.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(userDto.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(userDto.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(userDto.getAge()).isEqualTo(userEntity.getAge());

    }

    @Test
    public void getUserByEmailNullTest(){

        UserEntity userEntity = null;

        String email = "mail@mail.com";

        when(usersRepository.findByEmail(email)).thenReturn(userEntity);

        UserDto userDto = userService.getUserByEmail(email);

        assertThat(userDto).isNull();

    }

    @Test
    public void getUsersByFirstNameTest(){

        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name", "LastName1",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserEntity userEntity1 = new UserEntity(2, "First Name", "LastName1",
                "mail1@mail.com", 38);

        Iterable<UserEntity> userEntities = Arrays.asList(userEntity, userEntity1);

        String firstName = "First Name";

        when(usersRepository.findByFirstName(firstName)).thenReturn(userEntities);
        when(userService.getUsersByFirstName(firstName)).thenReturn(userDtoList);
        when(utils.getUserDtoList(userEntities)).thenReturn(userDtoList);

        List<UserDto> userDtos = userService.getUsersByFirstName(firstName);

        assertThat(userDtos.get(0).getFirstName()).isEqualTo(userDto1.getFirstName());
        assertThat(userDtos.get(0).getEmail()).isEqualTo(userDto1.getEmail());
        assertThat(userDtos.get(0).getLastName()).isEqualTo(userDto1.getLastName());
        assertThat(userDtos.get(0).getAge()).isEqualTo(userDto1.getAge());
    }

    @Test
    public void getUsersByLastNameTest(){

        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name1", "LastName",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserEntity userEntity1 = new UserEntity(2, "First Name1", "LastName",
                "mail1@mail.com", 38);

        Iterable<UserEntity> userEntities = Arrays.asList(userEntity, userEntity1);

        String lastName = "LastName";

        when(usersRepository.findByLastName(lastName)).thenReturn(userEntities);
        when(userService.getUsersByLastName(lastName)).thenReturn(userDtoList);
        when(utils.getUserDtoList(userEntities)).thenReturn(userDtoList);

        List<UserDto> userDtos = userService.getUsersByLastName(lastName);

        assertThat(userDtos.get(0).getFirstName()).isEqualTo(userDto1.getFirstName());
        assertThat(userDtos.get(0).getEmail()).isEqualTo(userDto1.getEmail());
        assertThat(userDtos.get(0).getLastName()).isEqualTo(userDto1.getLastName());
        assertThat(userDtos.get(0).getAge()).isEqualTo(userDto1.getAge());
    }

    @Test
    public void getUsersByFirstNameAndLastNameTest(){

        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name1", "LastName",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserEntity userEntity1 = new UserEntity(2, "First Name1", "LastName",
                "mail1@mail.com", 38);

        Iterable<UserEntity> userEntities = Arrays.asList(userEntity, userEntity1);

        String firstName = "First Name";
        String lastName = "LastName";

        when(usersRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(userEntities);
        when(userService.getUsersByFirstNameAndLastName(firstName, lastName)).thenReturn(userDtoList);
        when(utils.getUserDtoList(userEntities)).thenReturn(userDtoList);

        List<UserDto> userDtos = userService.getUsersByFirstNameAndLastName(firstName, lastName);

        assertThat(userDtos.get(0).getFirstName()).isEqualTo(userDto1.getFirstName());
        assertThat(userDtos.get(0).getEmail()).isEqualTo(userDto1.getEmail());
        assertThat(userDtos.get(0).getLastName()).isEqualTo(userDto1.getLastName());
        assertThat(userDtos.get(0).getAge()).isEqualTo(userDto1.getAge());

    }

    @Test
    public void getUserByIdTest(){
        Optional<UserEntity> userEntity = Optional.of(new UserEntity(1, "First Name", "LastName",
                "mail1@mail.com", 48));

        when(usersRepository.findById(1L)).thenReturn(userEntity);

        UserDto userDto1 = userService.getUserById(1L);

        assertThat(userDto1.getFirstName()).isEqualTo(userEntity.get().getFirstName());
        assertThat(userDto1.getEmail()).isEqualTo(userEntity.get().getEmail());
        assertThat(userDto1.getLastName()).isEqualTo(userEntity.get().getLastName());
        assertThat(userDto1.getAge()).isEqualTo(userEntity.get().getAge());

    }

    @Test
    public void createUserTest(){
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);

        UserDto userDto1 = userService.createUser(userDto);

        assertThat(userDto1.getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(userDto1.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(userDto1.getLastName()).isEqualTo(userDto.getLastName());
        assertThat(userDto1.getAge()).isEqualTo(userDto.getAge());
    }

    @Test
    public void deleteUserByEmailTest(){
        String email = "email@mail.com";
        doNothing().when(usersRepository).deleteByEmail(email);
        boolean res = userService.deleteUserByEmail(email);
        assertThat(res).isEqualTo(true);
    }
    @Test
    public void deleteUserByEmailExceptionTest() {
        String email = "email@mail.com";
        doThrow(RuntimeException.class).when(usersRepository).deleteByEmail(email);
        boolean res = userService.deleteUserByEmail(email);
        assertThat(res).isEqualTo(false);
    }

}
