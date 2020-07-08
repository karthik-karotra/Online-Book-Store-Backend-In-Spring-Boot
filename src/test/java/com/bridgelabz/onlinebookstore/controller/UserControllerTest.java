package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResetPasswordDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.CouponRepository;
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

    @MockBean
    CouponRepository couponRepository;

    Gson gson = new Gson();

    @Test
    public void givenUserDetailsToRegisterUser_WhenRegistered_ShouldReturnCorrectMessage() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false, UserRole.USER);
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
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false,UserRole.USER);
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

    @Test
    public void givenRequestToVerifyUser_WhenVerified_ThenReturnCorrectMessage() throws Exception {
        String message = "Account Verified";
        String jsonString = gson.toJson(message);
        when(userService.emailVerification(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(get("/user/register/confirmation/?token=xyz123")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Account Verified"));
    }

    @Test
    public void givenRequestToResendEmailForVerification_ThenReturnCorrectResponse() throws Exception {
        String message = "Verification Link Has Been Sent To Your Account";
        String jsonString = gson.toJson(message);
        when(userService.resendConfirmation(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/register/resend/confirmation/ram@gmail.com")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Verification Link Has Been Sent To Your Account"));
    }

    @Test
    public void givenRequestForForgetPassword_ThenReturnCorrectResponseMessage() throws Exception {
        String message = "We've Sent A Password Reset Link To Your Email Address";
        String jsonString = gson.toJson(message);
        when(userService.forgotPassword(any(), any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/forgot/password/ram@gmail.com")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("We've Sent A Password Reset Link To Your Email Address"));
    }

    @Test
    public void givenRequestForResetPassword_WhenPasswordResetted_ThenReturnCorrectResponseMessage() throws Exception {
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO("Ram@12345");
        String toJson = gson.toJson(resetPasswordDTO);
        String message = "Password Reseted Successfully";
        when(userService.resetPassword(any(), any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/reset/password/?token=abc123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Password Reseted Successfully"));
    }
}


