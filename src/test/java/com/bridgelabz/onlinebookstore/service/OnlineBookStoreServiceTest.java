package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IAdminRepository;
import com.bridgelabz.onlinebookstore.service.implementors.OnlineBookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OnlineBookStoreServiceTest {

    @Mock
    IAdminRepository onlineBookStoreRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    OnlineBookStoreService onlineBookStoreService;

    BookDTO bookDTO;

    @Test
    public void givenRequestToGetListOfBookDetailsFromDatabase_WhenGetResponse_ShouldReturnListOfBookDetails() {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreRepository.findAll()).thenReturn(booksList);
        List<BookDetails> allBooks = onlineBookStoreService.getAllBooks(0, 10);
        Assert.assertEquals(booksList, allBooks);
    }

    @Test
    public void givenRequestToGetCountOfBooksInDatabase_WhenGetResponse_ShouldReturnCountOfBooks() {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreRepository.findAll()).thenReturn(booksList);
        Integer countOfBooks = onlineBookStoreService.getCountOfBooks(0, 10);
        Assert.assertEquals("1", countOfBooks.toString());
    }

    @Test
    public void givenRequestToGetListOfBookDetailsFromDatabase_WhenNoBooksFound_ShouldThrowException() {
        try {
            List booksList = new ArrayList();
            when(onlineBookStoreRepository.findAll()).thenReturn(booksList);
            onlineBookStoreService.getAllBooks(0, 10);
        } catch (OnlineBookStoreException ex) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }

    }
}
