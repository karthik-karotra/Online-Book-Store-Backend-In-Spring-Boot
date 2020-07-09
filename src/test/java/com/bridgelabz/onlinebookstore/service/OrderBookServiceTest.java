package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.OrderException;
import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import com.bridgelabz.onlinebookstore.models.*;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.*;
import com.bridgelabz.onlinebookstore.service.implementations.OrderBookService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderBookServiceTest {

    @MockBean
    OrderBookRepository orderBookRepository;

    @Autowired
    OrderBookService orderBookService;

    @MockBean
    OnlineBookStoreRepository onlineBookStoreRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomerDetailsRepository customerDetailsRepository;

    @MockBean
    JavaMailSender javaMailSender;

    @MockBean
    OrderProductRepository orderProductRepository;

    @MockBean
    BookCartRepository bookCartRepository;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    ITokenGenerator tokenGenerator;

    BookDTO bookDTO;
    BookDetails bookDetails;
    CustomerDTO customerDTO;
    CustomerDetails customerDetails;
    UserRegistrationDTO userRegistrationDTO;
    UserDetails userDetails;
    List<CustomerDetails> customerDetailsList;
    BookCart bookCart;
    List<BookCart> bookCartList;

    public OrderBookServiceTest() {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        bookDetails = new BookDetails(bookDTO);
        bookDetails.id = 1;
        customerDTO = new CustomerDTO("Sai Prerah Apt", "Mumbai", "400703", "Navratna Hotel", "Vashi", "HOME");
        customerDetails = new CustomerDetails(customerDTO);
        userRegistrationDTO = new UserRegistrationDTO("Karthik", "karthikpatel54@gmail.com", "Karthik@123", "9874521478", false, UserRole.USER);
        userDetails = new UserDetails(userRegistrationDTO);
        customerDetailsList = new ArrayList<>();
        customerDetailsList.add(customerDetails);
        bookCart = new BookCart(bookDetails, 1);
        bookCartList = new ArrayList<>();
        bookCartList.add(bookCart);
    }

    @Test
    void givenCustomerOrderDetailsToAddInDatabase_WhenAdded_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(userDetails));
        when(cartRepository.findByUser(any())).thenReturn(java.util.Optional.of(new CartDetails()));
        when(customerDetailsRepository.findByUserDetailsOrderByIdDesc(any())).thenReturn(customerDetailsList);
        when(bookCartRepository.findAllByCart(any())).thenReturn(bookCartList);
        when(orderProductRepository.save(any())).thenReturn(new OrderProduct());
        doNothing().when(onlineBookStoreRepository).updateStock(anyInt(), anyInt());
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        String message = orderBookService.addOrderSummary(100.0,"CB100","authorization");
        Assert.assertEquals("Successfully Placed Order", message);
    }

    @Test
    void givenRequestToGetCustomerOrderDetails_ShouldReturnOrderDetails() {
        OrderBookDetails orderBookDetails = new OrderBookDetails();
        List<OrderBookDetails> orderBookDetailsList = new ArrayList<>();
        orderBookDetailsList.add(orderBookDetails);
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(userDetails));
        when(orderBookRepository.findByUserDetails(any())).thenReturn(orderBookDetailsList);
        List<OrderBookDetails> orderBookDetailsList1 = orderBookService.getOrders("authorization");
        Assert.assertEquals(orderBookDetailsList, orderBookDetailsList1);
    }

    @Test
    void givenRequestToGetCustomerOrderDetails_WhenNoOrdersPresent_ShouldThrowException() {
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(userDetails));
            when(orderBookRepository.findByUserDetails(any())).thenThrow(new OrderException("No Books Are Ordered Yet", OrderException.ExceptionType.NO_ORDER_PLACED));
            orderBookService.getOrders("authorization");
        } catch (OrderException ex) {
            Assert.assertEquals(OrderException.ExceptionType.NO_ORDER_PLACED, ex.type);
        }
    }
}
