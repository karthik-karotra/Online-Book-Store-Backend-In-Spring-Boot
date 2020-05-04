package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class OnlineBookStoreServiceTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    IOnlineBookStoreService onlineBookStoreService;

    @MockBean
    private IOnlineBookStoreRepository onlineBookStoreRepository;

    BookDTO bookDTO;

    @Test
    public void givenBookDetails_WhenGetResponse_ShouldReturnBookDetails() throws Exception{
        bookDTO=new BookDTO( 1000,"Mrutyunjay","Shivaji Sawant",400.0,10,"Devotional","bfjadlbfajlal",2002);
        BookDetails bookDetails=new BookDetails(bookDTO);
        when(onlineBookStoreRepository.save(any())).thenReturn(bookDetails);
        BookDetails addBook = onlineBookStoreService.addBook(bookDTO);
        Assert.assertEquals(bookDetails,addBook);
    }
}