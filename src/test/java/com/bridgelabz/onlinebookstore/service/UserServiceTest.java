package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.ResetPasswordDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.JWTException;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.UserService;
import com.bridgelabz.onlinebookstore.utils.IEmailService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @MockBean
    ITokenGenerator tokenGenerator;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    JavaMailSender javaMailSender;

    @MockBean
    IEmailService emailService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    HttpServletRequest httpServletRequest;

    UserRegistrationDTO userRegistrationDTO;
    UserLoginDTO userLoginDTO;

    public UserServiceTest() {
        userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false, UserRole.USER);
        userLoginDTO = new UserLoginDTO("karthik@gmail.com", "Karthik@123");
    }

    @Test
    void givenUserDetailsToRegisterUser_WhenUserRegisters_ShouldReturnCorrectMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.id=1;
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(userDetails);
        when(httpServletRequest.getHeader(any())).thenReturn("verify");
        when(applicationProperties.getJwtVerificationExpiration()).thenReturn(600000);
        when(tokenGenerator.generateToken(anyInt(), anyInt())).thenReturn("token1");
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        String message = userService.addUser(userRegistrationDTO);
        Assert.assertEquals("Registration Successfull !! Please Check Your Registered Email For Email Verification",message);
    }

    @Test
    void givenUserDetailsToRegisterUser_WhenUserAlreadyExists_ShouldThrowException() {
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        try {
            when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(userDetails));
            when(userRepository.save(any())).thenReturn(new UserDetails());
            userService.addUser(userRegistrationDTO);
        } catch (UserException ex) {
            Assert.assertEquals(UserException.ExceptionType.USER_ALREADY_EXISTS, ex.type);
        }
    }

    @Test
    void givenUserDetailsToLoginUser_WhenUserLoggedIn_ShouldReturnCorrectMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.status = true;
        UserLoginDTO userLoginDTO = new UserLoginDTO("karthik@gmail.com", "Karthik@123");
        userDetails.id = 1;
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(userDetails));
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(applicationProperties.getJwtLoginExpiration()).thenReturn(86400000);
        when(tokenGenerator.generateToken(anyInt(), anyInt())).thenReturn("token1");
        String token = userService.userLogin(userLoginDTO);
        Assert.assertEquals("token1", token);
    }

    @Test
    void givenUserDetailsToLoginUser_WhenIncorrectPasswordEntered_ShouldThrowException() {
        UserDetails userDetails = new UserDetails(userLoginDTO);
        userDetails.status = true;
        try {

            when(userRepository.findByEmail(userLoginDTO.email)).thenReturn(java.util.Optional.of(userDetails));
            when(bCryptPasswordEncoder.matches(userLoginDTO.password, userDetails.password)).thenReturn(false);
            userService.userLogin(userLoginDTO);
        } catch (UserException ex) {
            Assert.assertEquals(UserException.ExceptionType.PASSWORD_INVALID, ex.type);
        }
    }

    @Test
    void givenUserDetailsToLoginUser_WhenIncorrectEmailEntered_ShouldThrowException() {
        try {
            when(userRepository.findByEmail(userLoginDTO.email)).thenThrow(new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND));
            userService.userLogin(userLoginDTO);
        } catch (UserException ex) {
            Assert.assertEquals(UserException.ExceptionType.EMAIL_NOT_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToVerifyUser_WhenUserVerified_ShouldReturnCorrectMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(userRepository.save(any())).thenReturn(userDetails);
        String message = userService.emailVerification("authorization");
        Assert.assertEquals("Account Verified", message);
    }

    @Test
    void givenRequestToVerifyUser_WhenUserAlreadyVerifiedWithSameEmail_ShouldThrowException() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false, UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(anyInt())).thenThrow(new JWTException("User With Same Email Id Already Exists", JWTException.ExceptionType.USER_NOT_FOUND));
            when(userRepository.save(any())).thenReturn(userDetails);
            userService.emailVerification("authorization");
        } catch (JWTException ex) {
            Assert.assertEquals(JWTException.ExceptionType.USER_NOT_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToResendVerificationLinkOfUser_WhenSuccessful_ShouldReturnCorrectMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.id = 1;
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(userDetails));
        when(tokenGenerator.generateToken(anyInt(), anyInt())).thenReturn("token1");
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        String message = userService.resendConfirmation("karthik@gmail.com");
        Assert.assertEquals("Verification Link Has Been Sent To Your Account", message);
    }

    @Test
    void givenRequestToResendVerificationLinkOfUser_WhenEmailIdNotFound_ShouldThrowException() {
        try {
            when(userRepository.findByEmail(any())).thenThrow(new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND));
            userService.resendConfirmation("karthik@gmail.com");
        } catch (UserException ex) {
            Assert.assertEquals(UserException.ExceptionType.EMAIL_NOT_FOUND, ex.type);
        }
    }

    //
    @Test
    void givenRequestToResetPassword_WhenEmailAddressExistsInDatabase_ShouldReturnCorrectResponseMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.id = 1;
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userDetails));
        when(httpServletRequest.getHeader(any())).thenReturn("passwordforget");
        when(applicationProperties.getJwtVerificationExpiration()).thenReturn(600000);
        when(tokenGenerator.generateToken(anyInt(), anyInt())).thenReturn("token1");
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        String existingBook = userService.forgotPassword("karthik@gmail.com", httpServletRequest);
        Assert.assertEquals("We've Sent A Password Reset Link To Your Email Address", existingBook);
    }

    @Test
    void givenRequestOfForgetPassword_WhenEmailNotFound_ShouldThrowException() {
        try {
            HttpServletRequest httpServletRequest = null;
            when(userRepository.findByEmail(any())).thenThrow(new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND));
            userService.forgotPassword("karthik@gmail.com", httpServletRequest);
        } catch (UserException ex) {
            Assert.assertEquals(UserException.ExceptionType.EMAIL_NOT_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToResetPassword_WhenPasswordResetted_ShouldReturnCorrectMessage() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false,UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("Password@123");
        when(userRepository.save(any())).thenReturn(new UserDetails());
        String message = userService.resetPassword(resetPasswordDTO, "token");
        Assert.assertEquals("Password Reseted Successfully", message);
    }
}
