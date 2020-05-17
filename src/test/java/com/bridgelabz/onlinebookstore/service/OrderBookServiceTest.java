package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.service.implementors.OrderBookService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderBookServiceTest {
    @Mock
    OrderBookRepository orderBookRepository;

    @InjectMocks
    OrderBookService orderBookService;

    OrderBookDTO orderBookDTO;

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectDetails() {
        orderBookDTO = new OrderBookDTO(1, 5, 2500.0, "Karthik", "810854124", "400754", "Vashi", "Sector 17,Mon Biju", "Navi Mumbai", "Navratna Hotel", "Home");
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        when(orderBookRepository.save(any())).thenReturn(orderBookDetails);
        OrderBookDetails orderBookDetails1 = orderBookService.addOrderSummary(orderBookDTO);
        Assert.assertEquals( orderBookDetails, orderBookDetails1);
    }
}
