package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.service.implementations.OrderBookService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class OrderBookServiceTest {
    @Mock
    OrderBookRepository orderBookRepository;

    @InjectMocks
    OrderBookService orderBookService;

    OrderBookDTO orderBookDTO;

 /*   @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Avatar", "8108541248", "416523", "Sindhudurga", "Vengurla", "Vengurla", "Arebian Sea", "Home");
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookRepository.save(any())).thenReturn(orderBookDetails);
        OrderBookDetails orderBookDetails1 = orderBookService.addOrderSummary(orderBookDTO);
        Assert.assertEquals( orderBookDetails, orderBookDetails1);
    }*/
}
