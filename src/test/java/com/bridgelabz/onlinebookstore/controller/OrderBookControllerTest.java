package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IOrderBookService orderBookService;

    OrderBookDTO orderBookDTO;
    Gson gson = new Gson();

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "8108541248", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/order").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Karthik"));
    }

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnStatusOk() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "8108541248", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        this.mockMvc.perform(post("/bookstore/order")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenIncorrectUrlPathForOrderBook_ShouldReturnURLNotFound() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "8108541248", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        this.mockMvc.perform(post("/store/order")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenDataWithoutJsonConversionForOrderBook_ShouldReturn400StatusCode() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "8108541248", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        // String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/order")
                .content(String.valueOf(orderBookDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    void givenContentTypeOfAnotherTypeForOrderBook_ShouldReturnUnsupporteMediaType() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Avatar", "8108541248", "416523", "Sindhudurga", "Vengurla", "Vengurla", "Arebian Sea", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        this.mockMvc.perform(post("/bookstore/order")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void givenIncorrectRequestBodyForOrderBook_ShouldReturnMethodNotAllowed() throws Exception {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Avatar", "8108541248", "416523", "Sindhudurga", "Vengurla", "Vengurla", "Arebian Sea", "Home");
        String jsonDto = gson.toJson(orderBookDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookService.addOrderSummary(any())).thenReturn(orderBookDetails);
        this.mockMvc.perform(get("/bookstore/order")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }
}
