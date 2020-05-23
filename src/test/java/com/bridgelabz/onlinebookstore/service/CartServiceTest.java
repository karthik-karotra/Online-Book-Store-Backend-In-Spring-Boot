package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.CartException;
import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.models.CartDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.BookCartRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.CartService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceTest {

    @MockBean
    CartRepository cartRepository;

    @Autowired
    CartService cartService;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    ITokenGenerator tokenGenerator;

    @MockBean
    UserRepository userRepository;

    @MockBean
    OnlineBookStoreRepository onlineBookStoreRepository;

    @MockBean
    BookCartRepository bookCartRepository;

    BookDTO bookDTO;
    CartDetails cartDetails;
    BookCart bookCart;
    List<BookCart> bookCartList;
    BookDetails bookDetails;

    public CartServiceTest() {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        bookDetails = new BookDetails(bookDTO);
        cartDetails = new CartDetails();
        bookDetails.id = 1;
        bookCart = new BookCart(bookDetails, 1);
        bookCartList = new ArrayList<>();
        bookCartList.add(bookCart);
        cartDetails.setId(1);
        cartDetails.setBookCarts(bookCartList);
    }

    @Test
    void givenRequestToAddBooksToCart_WhenGetResponse_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserDetails()));
        when(onlineBookStoreRepository.findById(any())).thenReturn(Optional.of(bookDetails));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
        when(cartRepository.save(any())).thenReturn(cartDetails);
        when(bookCartRepository.save(any())).thenReturn(bookCart);
        String message = cartService.saveBooksToCart(1, 1, "authorization");
        Assert.assertEquals("Book Successfully Added To Cart", message);
    }

    @Test
    void givenRequestToAddBooksToCart_WhenNoBookFound_ShouldThrowException() {
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(Optional.of(new UserDetails()));
            when(onlineBookStoreRepository.findById(any())).thenThrow(new CartException("Book Not Found", CartException.ExceptionType.NO_BOOK_FOUND));
            when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
            when(cartRepository.save(any())).thenReturn(cartDetails);
            when(bookCartRepository.save(any())).thenReturn(bookCart);
            cartService.saveBooksToCart(1, 1, "authorization");
        } catch (CartException ex) {
            Assert.assertEquals(CartException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToFetchAllBooksFromCart_WhenGetResponse_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
        when(bookCartRepository.findAllByCart(cartDetails)).thenReturn(bookCartList);
        List<BookCart> bookCartList1 = cartService.getAllBooks("token");
        Assert.assertEquals(bookCartList, bookCartList1);
    }

    @Test
    void givenRequestToFetchAllBooksFromCart_WhenNoBookFound_ShouldThrowException() {
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
            when(bookCartRepository.findAllByCart(cartDetails)).thenThrow(new CartException("No Books Found In Cart", CartException.ExceptionType.NO_BOOK_FOUND));
            cartService.getAllBooks("token");
        } catch (CartException ex) {
            Assert.assertEquals(CartException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

    @Test
    void givenBookCartIdAndQuantityToUpdateQuantityOfBookInCart_WhenUpdated_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
        when(bookCartRepository.findById(1)).thenReturn(Optional.ofNullable(bookCart));
        when(cartRepository.save(any())).thenReturn(new BookCart());
        String message = cartService.updateQuantity(1, 2, "token");
        Assert.assertEquals("Cart Updated Successfully", message);
    }

    @Test
    void givenBookCartIdAndQuantityToUpdateQuantityOfBookInCart_WhenBookNotFound_ShouldThrowException() {
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
            when(bookCartRepository.findById(1)).thenThrow(new CartException("No Book Found At Given Id", CartException.ExceptionType.NO_BOOK_FOUND));
            when(cartRepository.save(any())).thenReturn(new BookCart());
            cartService.updateQuantity(1, 2, "token");
        } catch (CartException ex) {
            Assert.assertEquals(CartException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

    @Test
    void givenRequestToDeleteBookFromCart_WhenDeleted_ShouldReturnCorrectResponseMessage() {
        when(tokenGenerator.getId(any())).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
        when(bookCartRepository.findById(1)).thenReturn(Optional.ofNullable(bookCart));
        when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
        String message = cartService.deleteBookFromCart(1, "token");
        Assert.assertEquals("Deleted Successfully", message);
    }

    @Test
    void givenRequestToDeleteBookFromCart_WhenNoBookFoundAtId_ShouldThrowException() {
        try {
            when(tokenGenerator.getId(any())).thenReturn(1);
            when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserDetails()));
            when(bookCartRepository.findById(1)).thenThrow(new CartException("No Book Found At Given Id", CartException.ExceptionType.NO_BOOK_FOUND));
            when(cartRepository.findByUser(any())).thenReturn(Optional.of(cartDetails));
            cartService.deleteBookFromCart(1, "token");
        } catch (CartException ex) {
            Assert.assertEquals(CartException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

}

