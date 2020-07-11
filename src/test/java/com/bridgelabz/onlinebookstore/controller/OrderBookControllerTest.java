package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.CouponRepository;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
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

    @MockBean
    CouponRepository couponRepository;

    HttpHeaders httpHeaders = new HttpHeaders();
    Gson gson = new Gson();

    @Test
    public void givenRequestToAddOrderSummaryToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() throws Exception {
        httpHeaders.set("token", "Rsafjvj213");
        String message = "Successfully Placed Order";
        String jsonString = gson.toJson(message);
        ResponseDTO responseDTO = new ResponseDTO(message);
        String jsonResponseDTO = gson.toJson(responseDTO);
        when(orderBookService.addOrderSummary(any(), any(), any())).thenReturn(message);
        this.mockMvc.perform(post("/bookstore/order/100.0/?discountCoupon=CB100")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders))
                .andExpect(content().json(jsonResponseDTO));
    }

    @Test
    public void givenRequestToFetchListOfOrderDetailsFromDatabase_ShouldReturnListOfOrderDetailsInDatabase() throws Exception {
        httpHeaders.set("token", "Rsafjvj213");
        List<OrderBookDetails> orderBookDetailsList = new ArrayList();
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false, UserRole.USER);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        OrderBookDetails orderBookDetails = new OrderBookDetails();
        orderBookDetails.setUserDetails(userDetails);
        orderBookDetails.setOrderId(123456);
        orderBookDetails.setCustomerDetails(new CustomerDetails());
        orderBookDetailsList.add(orderBookDetails);
        String jsonDto = gson.toJson(orderBookDetailsList);
        when(orderBookService.getOrders(any())).thenReturn(orderBookDetailsList);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/orders")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("123456"));
    }
}

