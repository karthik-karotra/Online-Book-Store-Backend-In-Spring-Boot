package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.models.*;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.*;
import com.bridgelabz.onlinebookstore.service.implementations.OrderBookService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderBookServiceTest {

    @MockBean
    OrderBookRepository orderBookRepository;

    @Autowired
    OrderBookService orderBookService;

    @MockBean
    OnlineBookStoreRepository onlineBookStoreRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomerDetailsRepository customerDetailsRepository;

    @MockBean
    BookCartRepository bookCartRepository;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    ITokenGenerator tokenGenerator;

    CustomerDTO customerDTO;
    CustomerDetails customerDetails;
    UserRegistrationDTO userRegistrationDTO;
    UserDetails userDetails;
    List<CustomerDetails> customerDetailsList;

    public OrderBookServiceTest() {
        customerDTO = new CustomerDTO("Sai Prerah Apt", "Mumbai", "400703", "Navratna Hotel", "Vashi", "HOME");
        customerDetails = new CustomerDetails(customerDTO);
        userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false);
        userDetails = new UserDetails(userRegistrationDTO);
        customerDetailsList = new ArrayList<>();
        customerDetailsList.add(customerDetails);
    }

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(userDetails));
        when(cartRepository.findByUser(any())).thenReturn(java.util.Optional.of(new CartDetails()));
        when(customerDetailsRepository.findByUserDetailsOrderByIdDesc(any())).thenReturn(customerDetailsList);
        String message = orderBookService.addOrderSummary("authorization");
        Assert.assertEquals("Successfully Placed Order", message);
    }
}
