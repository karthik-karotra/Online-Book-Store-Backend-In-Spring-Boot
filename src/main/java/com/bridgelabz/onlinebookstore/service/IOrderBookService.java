package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;

public interface IOrderBookService {

    Integer addOrderSummary(OrderBookDTO... orderBookDTO);
}
