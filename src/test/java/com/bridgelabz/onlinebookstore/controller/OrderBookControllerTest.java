package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import com.bridgelabz.onlinebookstore.service.implementors.OrderBookService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IOrderBookService orderBookService;

    Gson gson = new Gson();
    OrderBookDTO orderBookDTO;

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "810854124", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/order").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Karthik"));
    }

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnStatusOk() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "810854124", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        this.mockMvc.perform(post("/bookstore/order")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
