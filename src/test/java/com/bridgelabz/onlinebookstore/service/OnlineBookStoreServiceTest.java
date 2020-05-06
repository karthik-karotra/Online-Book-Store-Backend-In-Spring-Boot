package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.implementors.OnlineBookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class  OnlineBookStoreServiceTest {

    @Mock
    IOnlineBookStoreRepository onlineBookStoreRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    OnlineBookStoreService onlineBookStoreService;

    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAdd_WhenGetResponse_ShouldReturnBookDetails() {
        bookDTO = new BookDTO(4321, "abc", "xyz", 500.0, 7, "Programming And Software Development", "Ms", 2007);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(onlineBookStoreRepository.save(any())).thenReturn(bookDetails);
        BookDetails saveBook = onlineBookStoreService.saveBook(bookDTO);
        Assert.assertEquals(bookDetails, saveBook);
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameIsbnNumber_ShouldThrowException() {
        try {
        bookDTO = new BookDTO(1111, "Java For Dummies", "Barry A. Burd", 500.0, 7, "Programming And Software Development", "Ms", 2007);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(onlineBookStoreRepository.findByIsbn(1111)).thenReturn(Optional.of(bookDetails));
        onlineBookStoreService.saveBook(bookDTO);
        } catch (OnlineBookStoreException e) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.ISBN_NO_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameBookAndAuthorName_ShouldThrowException() {
        try {
            bookDTO = new BookDTO(2222, "Java For Dummies", "Barry A. Burd", 500.0, 7, "Programming And Software Development", "Ms", 2007);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(onlineBookStoreRepository.findByBookNameAndAuthorName("Java For Dummies","Barry A. Burd")).thenReturn(Optional.of(bookDetails));
            onlineBookStoreService.saveBook(bookDTO);
        } catch (OnlineBookStoreException e) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS, e.type);
        }
    }
}