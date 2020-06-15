package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.AdminRepository;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.AdminBookStoreService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminBookStoreServiceTest {

    @MockBean
    AdminRepository onlineBookStoreRepository;

    @MockBean
    OrderBookRepository orderBookRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    ITokenGenerator tokenGenerator;

    @Autowired
    AdminBookStoreService adminBookStoreService;

    @MockBean
    ApplicationProperties applicationProperties;

    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAdd_WhenGetResponse_ShouldReturnBookDetails() {
        bookDTO = new BookDTO("9876543210", "Half Girlfriend", "Chetan Bhagat", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        when(onlineBookStoreRepository.save(any())).thenReturn(bookDetails);
        String message = "ADDED SUCCESSFULLY";
        String saveBook = adminBookStoreService.saveBook("token", bookDTO);
        Assert.assertEquals(message, saveBook);
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameIsbnNumber_ShouldThrowException() {
        try {
            bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            when(onlineBookStoreRepository.findByIsbn("1234567890")).thenReturn(Optional.of(bookDetails));
            adminBookStoreService.saveBook("token", bookDTO);
        } catch (AdminException e) {
            Assert.assertEquals(AdminException.ExceptionType.ISBN_NO_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameBookAndAuthorName_ShouldThrowException() {
        try {
            bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            when(onlineBookStoreRepository.findByBookNameAndAuthorName("Mrutyunjay", "Shivaji Sawant")).thenReturn(Optional.of(bookDetails));
            adminBookStoreService.saveBook("token", bookDTO);
        } catch (AdminException e) {
            Assert.assertEquals(AdminException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenImageToAddAtServerSide_WhenStoredInDirectory_ShouldReturnTrue() {
        when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.jpg", "text/plain", "some xml".getBytes());
        adminBookStoreService.uploadImage(multipartFile, "token");
        String path = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        Assert.assertTrue(new File(path).exists());
    }

    @Test
    void givenImageToAddAtServerSide_WhenNotStoredInDirectory_ShouldReturnFalse() {
        when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.jpg", "text/plain", "some xml".getBytes());
        adminBookStoreService.uploadImage(multipartFile, "token");
        String path = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        Assert.assertFalse(new File(path + "abc").exists());
    }

    @Test
    void givenWrongImageFileFormat_ShouldThrowException() {
        try {
            when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.pdf", "text/plain", "some xml".getBytes());
            String path = adminBookStoreService.uploadImage(multipartFile, "token");
        } catch (AdminException ex) {
            Assert.assertEquals(AdminException.ExceptionType.INCOMPATIBLE_TYPE, ex.type);
        }
    }

    @Test
    public void givenRequestToGetListOfOrderDetailsFromDatabase_WhenGetResponse_ShouldReturnListOfOrderDetails() {
        OrderBookDetails orderBookDetails = new OrderBookDetails();
        List<OrderBookDetails> orderBookDetailsList = new ArrayList();
        orderBookDetailsList.add(orderBookDetails);
        Pageable paging = PageRequest.of(0, 10);
        Page<OrderBookDetails> page = new PageImpl(orderBookDetailsList);
        Mockito.when(this.orderBookRepository.findAll(paging)).thenReturn(page);
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        List<OrderBookDetails> allBooks = adminBookStoreService.getOrders(0, 10, "token");
        Assert.assertEquals(orderBookDetailsList, allBooks);
    }

    @Test
    public void givenRequestToGetListOfOrderDetailsFromDatabase_WhenNoOrdersWerePlaced_ShouldThrowException() {
        try {
            Pageable paging = PageRequest.of(0, 10);
            Mockito.when(this.orderBookRepository.findAll(paging)).thenReturn(Page.empty());
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            adminBookStoreService.getOrders(0, 10, "token");
        } catch (AdminException ex) {
            Assert.assertEquals(AdminException.ExceptionType.NO_ORDER_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToUpdateOrderStatus_WhenUpdated_ShouldReturnCorrectResponseMessage() {
        OrderBookDetails orderBookDetails = new OrderBookDetails();
        when(orderBookRepository.findById(1)).thenReturn(Optional.ofNullable(orderBookDetails));
        when(orderBookRepository.save(any())).thenReturn(new OrderBookDetails());
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        String message = adminBookStoreService.updateOrderStatus(1, OrderStatus.DELIVERED, "token");
        Assert.assertEquals("Order Status Updated Successfully", message);
    }

    @Test
    void givenRequestToUpdateOrderStatus_WhenNoOrdersWerePlaced_ShouldThrowException() {
        try {
            when(orderBookRepository.findById(1)).thenThrow(new AdminException("No Order Of Given Id Found", AdminException.ExceptionType.NO_ORDER_FOUND));
            when(orderBookRepository.save(any())).thenReturn(new OrderBookDetails());
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            adminBookStoreService.updateOrderStatus(1, OrderStatus.DELIVERED, "token");
        } catch (AdminException ex) {
            Assert.assertEquals(AdminException.ExceptionType.NO_ORDER_FOUND, ex.type);
        }
    }
}