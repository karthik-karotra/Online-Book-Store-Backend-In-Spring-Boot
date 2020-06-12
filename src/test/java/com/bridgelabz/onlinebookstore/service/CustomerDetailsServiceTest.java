package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.CustomerDetailsRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.CustomerDetailsService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerDetailsServiceTest {

    @MockBean
    CustomerDetailsRepository customerDetailsRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    CustomerDetailsService customerDetailsService;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    ITokenGenerator tokenGenerator;

    CustomerDTO customerDTO;
    List<CustomerDetails> customerDetailsList = new ArrayList<>();
    UserRegistrationDTO userRegistrationDTO;
    UserDetails userDetails;
    CustomerDetails customerDetails;

    public CustomerDetailsServiceTest() {
        customerDTO = new CustomerDTO("Sai Prerah Apt", "Mumbai", "400704", "Navratna Hotel", "Vashi", "HOME");
        userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false, UserRole.USER);
        userDetails = new UserDetails(userRegistrationDTO);
        customerDetails = new CustomerDetails(customerDTO);
        customerDetails.setUserDetails(userDetails);
        userDetails.setCustomerDetails(customerDetailsList);
        customerDetailsList.add(customerDetails);
    }

    @Test
    void givenCustomerDetailsToAdd_WhenAdded_ShouldReturnCustomerDetailsAddedSuccessfully() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(Optional.of(userDetails));
        String message = customerDetailsService.addCustomerDetails(customerDTO, "authorization");
        Assert.assertEquals("Customer Details Added Successfully", message);
    }

    @Test
    void givenRequestToFetchCustomerDetails__ShouldReturnCustomerDetails() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(Optional.of(userDetails));
        when(customerDetailsRepository.findById(any())).thenReturn(Optional.of(customerDetails));
        UserDetails userDetails1 = customerDetailsService.getAllCustomers("authorization");
        Assert.assertEquals(userDetails, userDetails1);
    }
}
