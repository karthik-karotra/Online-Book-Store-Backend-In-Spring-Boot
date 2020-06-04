package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderBookController.class)
public class OrderBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IOrderBookService orderBookService;

    @MockBean
    public ApplicationProperties applicationProperties;

    Gson gson = new Gson();

    @Test
    public void givenRequestToAddOrderSummaryToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() throws Exception {
        String message = "Successfully Placed Order";
        String jsonString = gson.toJson(message);
        ResponseDTO responseDTO = new ResponseDTO(message);
        String jsonResponseDTO = gson.toJson(responseDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(message);
        this.mockMvc.perform(post("/bookstore/order")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseDTO));
    }

}

