package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.exceptions.CartException;
import com.bridgelabz.onlinebookstore.exceptions.JWTException;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.models.CartDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.repository.BookCartRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.ICartService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    BookCartRepository bookCartRepository;

    @Autowired
    ITokenGenerator tokenGenerator;

    @Autowired
    UserRepository userRepository;

    public UserDetails isUserPresent(String token) {
        int userId = tokenGenerator.getId(token);
        Optional<UserDetails> userDetailsById = userRepository.findById(userId);
        if (!userDetailsById.isPresent()) {
            throw new JWTException("User Not Found", JWTException.ExceptionType.USER_NOT_FOUND);
        }
        return userDetailsById.get();
    }

    @Override
    public List<BookCart> getAllBooks(String token) {
        UserDetails userDetails = isUserPresent(token);
        CartDetails cartDetails = cartRepository.findByUser(userDetails).get();
        List<BookCart> bookCartList = bookCartRepository.findAllByCart(cartDetails);
        if (bookCartList.size() == 0)
            throw new CartException("No Books Found In Cart", CartException.ExceptionType.NO_BOOK_FOUND);
        return bookCartList;
    }

    @Override
    public String saveBooksToCart(Integer quantity, Integer bookId, String token) {
        UserDetails userDetails = isUserPresent(token);
        Optional<BookDetails> optionalBookDetails = onlineBookStoreRepository.findById(bookId);
        if (!optionalBookDetails.isPresent())
            throw new CartException("Book Not Found", CartException.ExceptionType.NO_BOOK_FOUND);
        BookDetails bookDetails = optionalBookDetails.get();
        if (quantity > bookDetails.quantity )
            throw new CartException("Quantity Is Greater Than Stock", CartException.ExceptionType.ORDER_QUANTITY_GREATER_THEN_STOCK);
        BookCart bookCart = new BookCart(bookDetails, quantity);
        CartDetails cart = cartRepository.findByUser(userDetails).get();
        List bookCartList = new ArrayList();
        bookCartList.add(bookCart);
        cart.getBookCarts().add(bookCart);
        cart.setBookCarts(bookCartList);
        bookCart.setCart(cart);
        bookCartRepository.save(bookCart);
        cartRepository.save(cart);
        return "Book Successfully Added To Cart";
    }

    @Override
    public String updateQuantity(Integer bookCartId, Integer quantity, String token) {
        isUserPresent(token);
        Optional<BookCart> optionalBookCart = bookCartRepository.findById(bookCartId);
        if (!optionalBookCart.isPresent())
            throw new CartException("No Book Found At Given Id", CartException.ExceptionType.NO_BOOK_FOUND);
        BookCart bookCart = optionalBookCart.get();
        bookCart.setQuantity(quantity);
        bookCartRepository.save(bookCart);
        return "Cart Updated Successfully";
    }

    @Override
    public String deleteBookFromCart(Integer id, String token) {
        isUserPresent(token);
        Optional<BookCart> optionalBookCart = bookCartRepository.findById(id);
        if (!optionalBookCart.isPresent())
            throw new CartException("No Book Found At Given Id", CartException.ExceptionType.NO_BOOK_FOUND);
        BookCart bookCart = optionalBookCart.get();
        bookCartRepository.delete(bookCart);
        return "Deleted Successfully";
    }

    @Override
    public CartDetails createCart(UserDetails userDetails) {
        CartDetails cart = new CartDetails();
        cart.setUser(userDetails);
        cartRepository.save(cart);
        return cart;
    }
}
