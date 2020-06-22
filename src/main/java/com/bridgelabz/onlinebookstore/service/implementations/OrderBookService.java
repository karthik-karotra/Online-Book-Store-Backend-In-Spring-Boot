package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.exceptions.OrderException;
import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import com.bridgelabz.onlinebookstore.models.*;
import com.bridgelabz.onlinebookstore.repository.*;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import com.bridgelabz.onlinebookstore.utils.IEmailService;
import com.bridgelabz.onlinebookstore.utils.emailtemplates.OrderSuccessfulEmailTemplateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    OnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    IEmailService emailService;

    @Autowired
    CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    OrderSuccessfulEmailTemplateGenerator emailTemplateGenerator;


    @Override
    public String addOrderSummary(Double discountPrice,String token) {
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
        orderBookDetails.setOrderStatus(OrderStatus.ORDERED);
        List<BookCart> bookCartList = bookCartRepository.findAllByCart(cartDetails);
        Double totalPrice = calculateTotalPrice(bookCartList);
        orderBookDetails.setTotalPrice(totalPrice);
        orderBookRepository.save(orderBookDetails);
        updateQuantityOfBooks(bookCartList);
        addOrderProduct(bookCartList, orderBookDetails);
        sendOrderConfirmation(orderBookDetails,bookCartList,customerDetails,totalPrice,discountPrice);
        deleteBookCart(bookCartList);
        return "Successfully Placed Order";
    }

    private Double calculateTotalPrice(List<BookCart> bookCartList) {
        Double totalPrice=0.0;
        for (int i=0;i<bookCartList.size();i++) {
            Double amountOfOneBook=0.0;
            amountOfOneBook=bookCartList.get(i).getBook().bookPrice * bookCartList.get(i).getQuantity();
            totalPrice=totalPrice+amountOfOneBook;
        }
        return totalPrice;
    }

    private void sendOrderConfirmation(OrderBookDetails orderBookDetails, List<BookCart> bookCartList, CustomerDetails customerDetails, Double totalPrice,Double discountPrice) {
        String message = emailTemplateGenerator.getEmailTemplate(orderBookDetails.getUserDetails().getFullName(),bookCartList,customerDetails.getAddress()+ " "+customerDetails.getLandmark()+" "+customerDetails.getLocality()+" "+customerDetails.getCity()+"-"+customerDetails.getPincode(),totalPrice,discountPrice,orderBookDetails.getOrderId());
        emailService.notifyThroughEmail(orderBookDetails.getUserDetails().email, "Order Confirmation", message);
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

    @Override
    public List<OrderBookDetails> getOrders(String token) {
        UserDetails userDetails = cartService.isUserPresent(token);
        List<OrderBookDetails> orderBookDetailsList = orderBookRepository.findByUserDetails(userDetails);
        if (orderBookDetailsList.size() == 0)
            throw new OrderException("No Books Are Ordered Yet", OrderException.ExceptionType.NO_ORDER_PLACED);
        return orderBookDetailsList;
    }

    private Integer getOrderId() {
        List<OrderBookDetails> orderBookDetailsList = orderBookRepository.findAll();
        if (orderBookDetailsList.size() == 0) {
            return 100000;
        }
        List<OrderBookDetails> allBooks = orderBookDetailsList.stream()
                .sorted(Comparator.comparing(orderBookDetails -> orderBookDetails.orderId))
                .collect(Collectors.toList());
        Collections.reverse(allBooks);
        Integer orderId = allBooks.get(0).orderId + 1;
        return orderId;
    }
}
