package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OnlineBookStoreServiceTest {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @Mock
    IOnlineBookStoreRepository onlineBookStoreRepository;

    @Test
    public void givenBookDetailsToAdd_WhenGetResponse_ShouldReturnBookDetails() {
        BookDTO bookDTO = new BookDTO(1119, "Dummie", "B Burd", 500.0, 7, "Programming And Software Development", "Ms", 2007);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails saveBook = onlineBookStoreService.addBook(bookDTO);
        when(onlineBookStoreRepository.findByIsbn(bookDTO.getIsbn())).thenReturn(null);
        when(onlineBookStoreRepository.findByBookNameAndAuthorName(bookDTO.getBookName(), bookDTO.getAuthorName())).thenReturn(null);
        when(onlineBookStoreRepository.save(any(BookDetails.class))).thenReturn(null);
        Assert.assertEquals(bookDetails.authorName, saveBook.authorName);
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameIsbnNumber_ShouldThrowException() {
        try {
            BookDTO bookDTO = new BookDTO(1111, "Java For Dummies", "Barry A. Burd", 500.0, 7, "Programming And Software Development", "Ms", 2007);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(onlineBookStoreRepository.save(any(BookDetails.class))).thenReturn(bookDetails);
            onlineBookStoreService.addBook(bookDTO);
        } catch (OnlineBookStoreException e) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.ISBN_NO_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameBookAndAuthorName_ShouldThrowException() {
        try {
            BookDTO bookDTO = new BookDTO(2222, "Java For Dummies", "Barry A. Burd", 500.0, 7, "Programming And Software Development", "Ms", 2007);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(onlineBookStoreRepository.save(any(BookDetails.class))).thenReturn(bookDetails);
            onlineBookStoreService.addBook(bookDTO);
        } catch (OnlineBookStoreException e) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS, e.type);
        }
    }
}
