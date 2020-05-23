package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.models.CartDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import java.util.List;

public interface ICartService {
    List<BookCart> getAllBooks(String token);
    String saveBooksToCart(Integer quantity, Integer bookId, String token);
    String updateQuantity(Integer bookCartId, Integer quantity, String token);
    String deleteBookFromCart(Integer id, String token);
    CartDetails createCart(UserDetails userDetails);
}
