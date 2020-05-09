package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
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
    IOnlineBookStoreRepository onlineBookStoreRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    OnlineBookStoreService onlineBookStoreService;

    BookDTO bookDTO;

    @Test
    public void givenRequestToGetListOfBookDetailsFromDatabase_WhenGetResponse_ShouldReturnListOfBookDetails() {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreRepository.findAll()).thenReturn(booksList);
        List<BookDetails> allBooks = onlineBookStoreService.getAllBooks();
        Assert.assertEquals(booksList, allBooks);
    }

    @Test
    public void givenRequestToGetCountOfBooksInDatabase_WhenGetResponse_ShouldReturnCountOfBooks() {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreRepository.findAll()).thenReturn(booksList);
        Integer countOfBooks = onlineBookStoreService.getCountOfBooks();
        Assert.assertEquals("1",countOfBooks.toString());
    }
}
