package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.implementations.OnlineBookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OnlineBookStoreServiceTest {

    @MockBean
    OnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    OnlineBookStoreService onlineBookStoreService;

    @MockBean
    ApplicationProperties applicationProperties;

    BookDTO bookDTO;

    @Test
    public void givenRequestToGetListOfBookDetailsFromDatabase_WhenGetResponse_ShouldReturnListOfBookDetails() {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List<BookDetails> booksList = new ArrayList();
        booksList.add(bookDetails);
        Pageable paging = PageRequest.of(0, 10);
        Page<BookDetails> page = new PageImpl(booksList);
        Mockito.when(this.onlineBookStoreRepository.findAll(paging)).thenReturn(page);
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
        Integer countOfBooks = onlineBookStoreService.getCountOfBooks();
        Assert.assertEquals("1", countOfBooks.toString());
    }

    @Test
    public void givenRequestToGetListOfBookDetailsFromDatabase_WhenNoBooksFound_ShouldThrowException() {
        try {
            List booksList = new ArrayList();
            Pageable paging = PageRequest.of(0, 10);
            Page<BookDetails> page = new PageImpl(booksList);
            Mockito.when(this.onlineBookStoreRepository.findAll(paging)).thenReturn(Page.empty());
            onlineBookStoreService.getAllBooks(0, 10);
        } catch (OnlineBookStoreException ex) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

    @Test
    public void givenRequestToFilterBooksOnSearchedString_WhenNoBookFound_ShouldThrowException() {
        try {
            List<BookDetails> booksList = new ArrayList();
            when(this.onlineBookStoreRepository.findAllBooks(anyString())).thenReturn(booksList);
            onlineBookStoreService.findAllBooks("Ravinder", 0, FilterAttributes.LOW_TO_HIGH);
        } catch (OnlineBookStoreException ex) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND, ex.type);
        }
    }

    @Test
    void givenImageEmail_WhenImageFound_ShouldReturnTrue() throws MalformedURLException {
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/Images/1-world-best-bf.jpg");
        Resource resource = new UrlResource(path.toUri());
        when(this.applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
        Resource imageResponse = onlineBookStoreService.loadFileAsResource("1-world-best-bf.jpg");
        Assert.assertEquals(resource, imageResponse);
    }

    @Test
    void givenImageEmail_WhenImageNotFound_ShouldThrowException() throws MalformedURLException {
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/Images/1-world-best-bf.jpg");
            Resource resource = new UrlResource(path.toUri());
            when(this.applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
            Resource imageResponse = onlineBookStoreService.loadFileAsResource("1-abc.jpg");
        } catch (OnlineBookStoreException ex) {
            Assert.assertEquals(OnlineBookStoreException.ExceptionType.FILE_NOT_FOUND, ex.type);
        }
    }
}