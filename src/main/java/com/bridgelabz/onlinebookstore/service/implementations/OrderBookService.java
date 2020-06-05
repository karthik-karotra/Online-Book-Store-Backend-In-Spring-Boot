package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.exceptions.OrderException;
import com.bridgelabz.onlinebookstore.models.*;
import com.bridgelabz.onlinebookstore.repository.*;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import com.bridgelabz.onlinebookstore.utils.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderBookService implements IOrderBookService {
    @Autowired
    CartService cartService;

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    BookCartRepository bookCartRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    IEmailService emailService;

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;


    @Override
    public String addOrderSummary(String token) {
        Integer orderId = getOrderId();
        UserDetails userDetails = cartService.isUserPresent(token);
        CartDetails cartDetails = cartRepository.findByUser(userDetails).get();
        OrderBookDetails orderBookDetails = new OrderBookDetails();
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findByUserDetailsOrderByIdDesc(userDetails);
        CustomerDetails customerDetails = customerDetailsList.get(0);
        orderBookDetails.setOrderId(orderId);
        orderBookDetails.setUserDetails(userDetails);
        orderBookDetails.setCustomerDetails(customerDetails);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        orderBookDetails.setOrderDate(LocalDate.now().format(dateTimeFormatter));
        orderBookRepository.save(orderBookDetails);
        List<BookCart> bookCartList = bookCartRepository.findAllByCart(cartDetails);
        updateQuantityOfBooks(bookCartList);
        addOrderProduct(bookCartList, orderBookDetails);
        deleteBookCart(bookCartList);
        return "Successfully Placed Order";
    }

    private void addOrderProduct(List<BookCart> bookCartList, OrderBookDetails orderBookDetails) {
        bookCartList.stream().forEach(value -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setBook(value.getBook());
            orderProduct.setQuantity(value.getQuantity());
            orderProduct.setOrderBookDetails(orderBookDetails);
            orderProductRepository.save(orderProduct);
        });
    }

    private void deleteBookCart(List<BookCart> bookCartList) {
        bookCartList.stream().forEach(value -> {
            bookCartRepository.delete(value);
        });
    }

    private void updateQuantityOfBooks(List<BookCart> bookCartList) {
        bookCartList.stream().forEach(value -> {
            onlineBookStoreRepository.updateStock(value.getQuantity(), value.getBook().id);
        });
    }


    private Integer getOrderId() {
        boolean isOrderIdUnique = false;
        Integer orderId = 0;
        while (!isOrderIdUnique) {
            orderId = (int) Math.floor(100000 + Math.random() * 999999);
            Optional<OrderBookDetails> booksById = orderBookRepository.findByOrderId(orderId);
            if (!booksById.isPresent())
                isOrderIdUnique = true;
        }
        return orderId;
    }
}
