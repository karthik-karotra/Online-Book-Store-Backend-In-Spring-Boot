package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBookService implements IOrderBookService {

    @Autowired
    OrderBookRepository orderBookRepository;

    @Override
    public OrderBookDetails addOrderSummary(OrderBookDTO orderBookDTO) {
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookDTO);
        OrderBookDetails orderSummary = orderBookRepository.save(orderBookDetails);
        return orderSummary;
    }

}
