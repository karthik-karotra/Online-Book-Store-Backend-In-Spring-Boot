package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class OnlineBookStoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOnlineBookStoreService onlineBookStoreService;

    Gson gson=new Gson();

    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectStatus() throws Exception {
        bookDTO=new BookDTO( 1000,"Mrutyunjay","Shivaji Sawant",400.0,10,"Devotional","bfjadlbfajlal",2002);
        String jsonDto=gson.toJson(bookDTO);
        BookDetails bookDetails=new BookDetails(bookDTO);
        when(onlineBookStoreService.addBook(any())).thenReturn(bookDetails);
        MvcResult mvcResult = this.mockMvc.perform(post("/onlinebookstore/addbook").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }
}
