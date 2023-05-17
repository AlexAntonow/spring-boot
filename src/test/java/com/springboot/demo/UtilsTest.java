package com.springboot.demo;

import com.springboot.demo.data.UserEntity;
import com.springboot.demo.exception.NotFoundException;
import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import jakarta.validation.ValidationException;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTest {

    @Autowired
    Utils utils;


    @Test
    public void getUserResponseModelListTest() throws NotFoundException{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name1", "LastName1",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        List<UserResponseModel> usersList = utils.getUserResponseModelList(userDtoList);

        assertThat(userDtoList.get(0).getFirstName()).isEqualTo(usersList.get(0).getFirstName());
        assertThat(userDtoList.get(0).getEmail()).isEqualTo(usersList.get(0).getEmail());
        assertThat(userDtoList.get(0).getLastName()).isEqualTo(usersList.get(0).getLastName());
        assertThat(userDtoList.get(0).getAge()).isEqualTo(usersList.get(0).getAge());
    }


    @Test
    public void getUserResponseModelListNotFoundExceptionTest(){
        List<UserDto> list = null;

        assertThatThrownBy(() -> utils.getUserResponseModelList(list))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("0 records found");
    }

    @Test
    public void getUserResponseModelListNotFoundExceptionTest2(){
        List<UserDto> list = new ArrayList<>();

        assertThatThrownBy(() -> utils.getUserResponseModelList(list))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("0 records found");
    }

    @Test
    public void getUserResponseModelTest() throws NotFoundException{
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail1@mail.com", 18);

        UserResponseModel userResponseModel = utils.getUserResponseModel(userDto);

        assertThat(userDto.getFirstName()).isEqualTo(userResponseModel.getFirstName());
        assertThat(userDto.getEmail()).isEqualTo(userResponseModel.getEmail());
        assertThat(userDto.getLastName()).isEqualTo(userResponseModel.getLastName());
        assertThat(userDto.getAge()).isEqualTo(userResponseModel.getAge());

    }

    @Test
    public void getUserResponseModelNotFoundExceptionTest(){
        UserDto userDto = null;
        assertThatThrownBy(() -> utils.getUserResponseModel(userDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");

    }

    @Test
    public void getUserDtoTest() throws NotFoundException {
        UserRequestModel userDetails = new UserRequestModel("First Name", "LastName",
                "mail1@mail.com", 18);

        UserDto userDto = utils.getUserDto(userDetails);

        assertThat(userDto.getFirstName()).isEqualTo(userDetails.getFirstName());
        assertThat(userDto.getEmail()).isEqualTo(userDetails.getEmail());
        assertThat(userDto.getLastName()).isEqualTo(userDetails.getLastName());
        assertThat(userDto.getAge()).isEqualTo(userDetails.getAge());

    }

    @Test
    public void getUserDtoNotFoundExceptionTest(){
        UserRequestModel userDetails = null;

        assertThatThrownBy(() -> utils.getUserDto(userDetails))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Request object is null");
    }

    @Test
    public void getUserDtoListTest(){
        UserEntity userEntity = new UserEntity(1, "First Name", "LastName",
                "mail@mail.com", 18);

        UserEntity userEntity1 = new UserEntity(2, "First Name1", "LastName1",
                "mail1@mail.com", 48);

        Iterable<UserEntity> userEntities = Arrays.asList(userEntity, userEntity1);

        List<UserDto> allUsers = utils.getUserDtoList(userEntities);

        assertThat(allUsers.get(0).getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(allUsers.get(0).getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(allUsers.get(0).getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(allUsers.get(0).getAge()).isEqualTo(userEntity.getAge());

    }

    @Test
    public void getUserDtoListExceptionTest(){
        Iterable<UserEntity> userEntities = Arrays.asList();

        assertThatThrownBy(() -> utils.getUserDtoList(userEntities))
                .isInstanceOf(ValidationException.class)
                .hasMessage("List of entities is null or empty");

    }

}
