package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.service.ICustomerDetailsService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerDetailsController.class)
public class CustomerDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICustomerDetailsService customerDetailsService;

    @MockBean
    ApplicationProperties applicationProperties;

    Gson gson = new Gson();
    CustomerDTO customerDTO;

    @Test
    public void givenCustomerDetailsToAddInDatabase_WhenAdded_ThenShouldReturnCorrectMessage() throws Exception {
        customerDTO = new CustomerDTO("Sai Prerah Apt", "Mumbai", "400703", "Navratna Hotel", "Vashi", "HOME");
        String toJson = gson.toJson(customerDTO);
        String message = "Customer Details Added Successfully";
        when(customerDetailsService.addCustomerDetails(any(), any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Customer Details Added Successfully"));
    }

    @Test
    public void givenRequestToFetchCustomerDetailsFromDatabase_ShouldReturnCorrectData() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "8754212154", false);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        CustomerDTO customerDTO = new CustomerDTO("Sai Prerah Apt", "Mumbai", "400704", "Navratna Hotel", "Vashi", "HOME");
        CustomerDetails customerDetails = new CustomerDetails(customerDTO);
        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        customerDetailsList.add(customerDetails);
        userDetails.setCustomerDetails(customerDetailsList);
        String jsonDto = gson.toJson(userDetails);
        when(customerDetailsService.getAllCustomers(any())).thenReturn(userDetails);
        MvcResult mvcResult = this.mockMvc.perform(get("/customer")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Sai Prerah Apt"));
    }
}
