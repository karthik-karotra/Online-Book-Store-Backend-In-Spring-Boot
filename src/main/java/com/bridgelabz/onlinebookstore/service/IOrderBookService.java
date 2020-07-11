package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import java.util.List;

public interface IOrderBookService {
    String addOrderSummary(Double discountPrice, String coupon, String token);
    List<OrderBookDetails> getOrders(String token);
}
