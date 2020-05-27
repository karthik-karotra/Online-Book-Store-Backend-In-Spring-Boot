package com.bridgelabz.onlinebookstore.controller;


import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.service.implementations.UserService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    public ApplicationProperties applicationProperties;

    Gson gson = new Gson();

    @Test
    public void givenUserDetailsToRegisterUser_WhenRegistered_ShouldReturnCorrectMessage() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false);
        String toJson = gson.toJson(userRegistrationDTO);
        String message = "REGISTRATION SUCCESSFUL";
        when(userService.addUser(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("REGISTRATION SUCCESSFUL"));
    }

    @Test
    public void givenUserDetailsToRegisterUser_WhenInvalidData_ShouldThrowException() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false);
        String toJson = gson.toJson(userRegistrationDTO);
        when(userService.addUser(any())).thenThrow(new UserException("Invalid Data!!!!! Please Enter Valid Data", UserException.ExceptionType.INVALID_DATA));
        MvcResult mvcResult = this.mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Invalid Data!!!!! Please Enter Valid Data"));
    }

    @Test
    public void givenUserDetailsToLoginUser_WhenValidData_ShouldReturnCorrectMessage() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO("karthik@gmail.com", "Karthik@123");
        String toJson = gson.toJson(userLoginDTO);
        String message = "LOGIN SUCCESSFUL";
        when(userService.userLogin(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("LOGIN SUCCESSFUL"));
    }

    @Test
    public void givenUserDetailsToLoginUser_WhenInvalidData_ShouldThrowException() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO("karthik@gmail.com", "Karthik@123");
        String toJson = gson.toJson(userLoginDTO);
        String message = "LOGIN SUCCESSFUL";
        when(userService.userLogin(any())).thenThrow(new UserException("Invalid Data!!!!! Please Enter Valid Data", UserException.ExceptionType.INVALID_DATA));
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Invalid Data!!!!! Please Enter Valid Data"));
    }
}


