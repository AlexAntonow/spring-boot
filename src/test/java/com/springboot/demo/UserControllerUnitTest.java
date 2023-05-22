package com.springboot.demo;

import com.springboot.demo.controller.UserController;
import com.springboot.demo.exception.NotFoundException;
import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import com.springboot.demo.service.UserServiceImpl;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import jakarta.xml.bind.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerUnitTest {

    @InjectMocks
    UserController userController;

    @Mock
    Utils utils;

    @Mock
    UserServiceImpl userService;


    @Test
    public void getAllUsersTest() throws NotFoundException {
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name1", "LastName1",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        List<UserResponseModel> usersList = new ArrayList<>();
        userDtoList.forEach(userDtoTemp -> {
            UserResponseModel userResponseModel = new ModelMapper().map(userDtoTemp, UserResponseModel.class);
            usersList.add(userResponseModel);
        });

        when(userService.getAllUsers()).thenReturn(userDtoList);
        when(utils.getUserResponseModelList(userDtoList)).thenReturn(usersList);

        ResponseEntity<List<UserResponseModel>> responseEntity = userController.getAllUsers();
        List<UserResponseModel> userResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModelList != null;
        assertThat(userResponseModelList.get(0).getAge()).isEqualTo(18);
        assertThat(userResponseModelList.get(1).getAge()).isEqualTo(38);

    }

    @Test
    public void getUserByIdTest() throws NotFoundException {
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);

        UserResponseModel userResponseModel = new ModelMapper().map(userDto1, UserResponseModel.class);

        when(userService.getUserById(1)).thenReturn(userDto1);
        when(utils.getUserResponseModel(userDto1)).thenReturn(userResponseModel);

        ResponseEntity<UserResponseModel> userResponseModelResponseEntity = userController.getUserById(1);

        UserResponseModel userResponseModel1 = userResponseModelResponseEntity.getBody();
        assertThat(userResponseModelResponseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModel1 != null;
        assertThat(userResponseModel1.getEmail()).isEqualTo(userDto1.getEmail());

    }

    @Test
    public void getUserByEmailTest() throws NotFoundException{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto1, UserResponseModel.class);
        String email = "mail@mail.com";

        when(userService.getUserByEmail(email)).thenReturn(userDto1);
        when(utils.getUserResponseModel(userDto1)).thenReturn(userResponseModel);

        ResponseEntity<UserResponseModel> userResponseModelResponseEntity = userController.getUserByEmail(email);

        UserResponseModel userResponseModel1 = userResponseModelResponseEntity.getBody();
        assertThat(userResponseModelResponseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModel1 != null;
        assertThat(userResponseModel1.getEmail()).isEqualTo(email);

    }

    @Test
    public void searchUserTest() throws NotFoundException{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name", "LastName",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        String firstName = "First Name";
        String lastName = "LastName";

        List<UserResponseModel> usersList = new ArrayList<>();
        userDtoList.forEach(userDtoTemp -> {
            UserResponseModel userResponseModel = new ModelMapper().map(userDtoTemp, UserResponseModel.class);
            usersList.add(userResponseModel);
        });

        when(userService.getUsersByFirstNameAndLastName(firstName, lastName)).thenReturn(userDtoList);
        when(utils.getUserResponseModelList(userDtoList)).thenReturn(usersList);

        ResponseEntity<List<UserResponseModel>> responseEntity = userController.searchUsers(firstName, lastName);

        List<UserResponseModel> userResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModelList != null;
        assertThat(userResponseModelList.get(0).getAge()).isEqualTo(18);
        assertThat(userResponseModelList.get(1).getAge()).isEqualTo(38);

    }

    @Test
    public void searchUsersFirstName() throws NotFoundException{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name", "LastName",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        String firstName = "First Name";
        String lastName = null;

        List<UserResponseModel> usersList = new ArrayList<>();
        userDtoList.forEach(userDtoTemp -> {
            UserResponseModel userResponseModel = new ModelMapper().map(userDtoTemp, UserResponseModel.class);
            usersList.add(userResponseModel);
        });

        when(userService.getUsersByFirstName(firstName)).thenReturn(userDtoList);
        when(utils.getUserResponseModelList(userDtoList)).thenReturn(usersList);

        ResponseEntity<List<UserResponseModel>> responseEntity = userController.searchUsers(firstName, lastName);

        List<UserResponseModel> userResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModelList != null;
        assertThat(userResponseModelList.get(0).getAge()).isEqualTo(18);
        assertThat(userResponseModelList.get(1).getAge()).isEqualTo(38);
    }

    @Test
    public void searchUserLastNameTest() throws NotFoundException{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(2, "First Name", "LastName",
                "mail1@mail.com", 38);

        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        userDtoList.add(userDto);

        String firstName = null;
        String lastName = "LastName";

        List<UserResponseModel> usersList = new ArrayList<>();
        userDtoList.forEach(userDtoTemp -> {
            UserResponseModel userResponseModel = new ModelMapper().map(userDtoTemp, UserResponseModel.class);
            usersList.add(userResponseModel);
        });

        when(userService.getUsersByLastName(lastName)).thenReturn(userDtoList);
        when(utils.getUserResponseModelList(userDtoList)).thenReturn(usersList);

        ResponseEntity<List<UserResponseModel>> responseEntity = userController.searchUsers(firstName, lastName);

        List<UserResponseModel> userResponseModelList = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModelList != null;
        assertThat(userResponseModelList.get(0).getAge()).isEqualTo(18);
        assertThat(userResponseModelList.get(1).getAge()).isEqualTo(38);

    }

    @Test
    public void searchUsersNoNameTest() throws NotFoundException{
        List<UserDto> userDtoList = null;
        String firstName = null;
        String lastName = null;

        when(utils.getUserResponseModelList(userDtoList)).thenThrow(NotFoundException.class);

        try{
            userController.searchUsers(firstName, lastName);
        }catch (Exception ex){
            assertThat(ex).isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    public void createUserTest() throws NotFoundException{
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);

        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);

        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);

        //we have to create new user, here we are checking if user exists or not
        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(null);
        when(utils.getUserDto(userRequestModel)).thenReturn(userDto);
        when(userService.createUser(userDto)).thenReturn(userDto);
        when(utils.getUserResponseModel(userDto)).thenReturn(userResponseModel);

        ResponseEntity<UserResponseModel> responseEntity = userController.createUser(userRequestModel);

        UserResponseModel userResponseModel1 = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("201 CREATED");
        assert userResponseModel1 != null;
        assertThat(userResponseModel1.getEmail()).isEqualTo(userRequestModel.getEmail());

    }

    // ## Added ##
    @Test
    public void createUserExceptionTest(){
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);

        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);

        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(userDto);

        try{
            userController.createUser(userRequestModel);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("User with provided email already exists");
        }
    }

    @Test
    public void updateUserTest() throws NotFoundException{
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);

        //we have to update user, here we are checking if user exists or not
        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(userDto);
        when(utils.getUserDto(userRequestModel)).thenReturn(userDto);
        when(userService.createUser(userDto)).thenReturn(userDto);
        when(utils.getUserResponseModel(userDto)).thenReturn(userResponseModel);

        ResponseEntity<UserResponseModel> responseEntity = userController.updateUser(userRequestModel);
        UserResponseModel userResponseModel1 = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("200 OK");
        assert userResponseModel1 != null;
        assertThat(userResponseModel1.getEmail()).isEqualTo(userRequestModel.getEmail());

    }

    // ## Added ##
    @Test
    public void updateUserExceptionTest() throws NotFoundException{
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);
        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(null);

        try{
            userController.updateUser(userRequestModel);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("Failed to update, user not found");
        }
    }

    @Test
    public void deleteUserTest() {
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        String email = "mail@mail.com";

        when(userService.getUserByEmail(email)).thenReturn(userDto);
        when(userService.deleteUserByEmail(email)).thenReturn(true);
        ResponseEntity<Void> responseEntity = userController.delete(email);
        assertThat(responseEntity.getStatusCode().toString()).isEqualTo("204 NO_CONTENT");
    }

    // ## Added ##
    @Test
    public void deleteUserExceptionTest() {
        String email = "mail@mail.com";
        when(userService.getUserByEmail(email)).thenReturn(null);
        try{
            userController.delete(email);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("Failed to delete, user not found");
        }
    }

    // ## Added ##
    @Test
    public void deleteUserExceptionSecondTest() {
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        String email = "mail@mail.com";
        when(userService.getUserByEmail(email)).thenReturn(userDto);
        when(userService.deleteUserByEmail(email)).thenReturn(false);
        try{
            userController.delete(email);
        }catch (Exception ex){
            assertThat(ex.getMessage()).isEqualTo("An error occurred while executing request");
        }
    }
}
