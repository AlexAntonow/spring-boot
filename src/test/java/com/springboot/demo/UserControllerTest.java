package com.springboot.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.controller.UserController;
import com.springboot.demo.exception.NotFoundException;
import com.springboot.demo.model.UserRequestModel;
import com.springboot.demo.model.UserResponseModel;
import com.springboot.demo.service.UserService;
import com.springboot.demo.shared.UserDto;
import com.springboot.demo.shared.Utils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpServerErrorException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Utils utils;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getUserByIdTest() throws Exception{
        UserDto userDto1 = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto1, UserResponseModel.class);

        when(userService.getUserById(1)).thenReturn(userDto1);
        when(utils.getUserResponseModel(userDto1)).thenReturn(userResponseModel);

//        ResultActions resultActions =
                mockMvc.perform(get("/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("first-name", is(userDto1.getFirstName())));
    }

    @Test
    public void getUserByIdNotFoundTest() throws Exception{

        when(userService.getUserById(1)).thenReturn(null);
        when(utils.getUserResponseModel(null)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/users/id/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status", is("Failed")));
    }

    @Test
    public void createUserTest() throws Exception{
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);
        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(null);

        Utils utils1 = Mockito.mock(Utils.class, Mockito.withSettings().defaultAnswer(InvocationOnMock::callRealMethod));
        when(utils1.getUserDto(userRequestModel)).thenReturn(userDto);

        when(userService.createUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isCreated());

    }

    @Test
    public void createUserExceptionTest() throws Exception{
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "LastName",
                "mail@mail.com", 18);

        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("User with provided email already exists")));
    }

    @Test
    public void createUserFieldExceptionTest() throws Exception{
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "a",
                "mail@mail.com", 18);

        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("lastName")))
                .andExpect(jsonPath("$[0].message", is("Last name cannot be less than 2 characters")));
    }


    @Test
    public void createUserPathExceptionTest() throws Exception{
        UserDto userDto = new UserDto(1, "First Name", "LastName",
                "mail@mail.com", 18);
        UserRequestModel userRequestModel = new UserRequestModel("First Name", "Last Name",
                "mail@mail.com", 18);

        when(userService.getUserByEmail(userRequestModel.getEmail())).thenReturn(userDto);

        mockMvc.perform(delete("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(userRequestModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("Wrong http method or routing")));
    }


    @Test
    public void deleteUserGeneralExceptionTest() throws Exception{
        when(userService.deleteUserByEmail("mail@mail.com")).thenReturn(false);
        doThrow(HttpServerErrorException.InternalServerError.class).when(userService).getUserByEmail("mail@mail.com");
        mockMvc.perform(delete("/users/mail@mail.com"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", is("Failed")))
                .andExpect(jsonPath("message", is("An exception occurred while processing request")));
    }
}
