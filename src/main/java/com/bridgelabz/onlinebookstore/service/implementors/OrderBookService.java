package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderBookService implements IOrderBookService {

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    private OnlineBookStoreRepository onlineBookStoreRepository;


    @Override
    public Integer addOrderSummary(OrderBookDTO... orderBookDTO) {
        Integer orderId = getOrderId();
        Arrays.stream(orderBookDTO).forEach(value -> {
            Optional<BookDetails> byId = onlineBookStoreRepository.findById(value.bookId);
            if(value.quantity > byId.get().quantity)
                throw new OnlineBookStoreException("Book quantity is greater than stock",OnlineBookStoreException.ExceptionType.ORDER_QUANTITY_GREATER_THEN_STOCK);
        });
        List<OrderBookDetails> data = Arrays.stream(orderBookDTO).map(value -> {
            OrderBookDetails orderBookDetails = new OrderBookDetails(value);
            orderBookDetails.orderId = orderId;
            return orderBookRepository.save(orderBookDetails);
        }).collect(Collectors.toList());
        updateQuantityOfBooks(data);
        return data.get(0).orderId;
    }

    private Integer getOrderId() {
        boolean isUnique = false;
        Integer orderId = 0;
        while(!isUnique){
            orderId = (int) Math.floor(100000 + Math.random() * 900000);
            Optional<OrderBookDetails> byId = orderBookRepository.findByOrderId(orderId);
            if( !byId.isPresent())
                isUnique = true;
        }
        return orderId;
    }

    private void updateQuantityOfBooks(List<OrderBookDetails> orderBookDetails) {
        orderBookDetails.stream().forEach(value -> {
            onlineBookStoreRepository.updateStock( value.quantity, value.bookId);
        });
    }


}
