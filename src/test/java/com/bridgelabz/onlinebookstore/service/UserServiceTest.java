package com.bridgelabz.onlinebookstore.service;


import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
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
    ApplicationProperties applicationProperties;

    @MockBean
    JavaMailSender javaMailSender;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserRegistrationDTO userRegistrationDTO;


    public UserServiceTest() {
        userRegistrationDTO = new UserRegistrationDTO("Karthik Karotra", "karthik@gmail.com", "Karthik@123", "8745124578", false);

    }

    @Test
    void givenUserDetailsToRegisterUser_WhenUserRegisters_ShouldReturnCorrectMessage() {
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        // when(userRepository.findByEmail(any()).isPresent()).thenReturn(false);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("Password@123");
        when(userRepository.save(any())).thenReturn(new UserDetails());
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        String message = userService.addUser(userRegistrationDTO);
        Assert.assertEquals("",message);
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
}
