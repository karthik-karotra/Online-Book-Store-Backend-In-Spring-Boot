package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.implementations.OnlineBookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OnlineBookStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OnlineBookStoreService onlineBookStoreService;

    BookDTO bookDTO;
    BookDetails bookDetails;
    Gson gson = new Gson();

    @Test
    public void givenRequestToFetchListOfBookDetailsFromDatabase_ShouldReturnListOfBookDetailsInDatabase() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List bookList = new ArrayList();
        bookList.add(bookDetails);
        String jsonDto = gson.toJson(bookList);
        ResponseDTO responseDTO = new ResponseDTO(bookList, "Response Successful");
        String jsonResponseDto = gson.toJson(responseDTO);
        when(onlineBookStoreService.getAllBooks(anyInt(), anyInt())).thenReturn(bookList);
        this.mockMvc.perform(get("/books/0")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponseDto));
    }

    @Test
    public void givenRequestToFetchListOfBooksFromDatabase_WhenGetResponse_ThenShouldReturnCorrectStatus() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreService.getAllBooks(0, 10)).thenReturn(booksList);
        this.mockMvc.perform(get("/books/0")).andExpect(status().isOk());
    }

    @Test
    public void givenRequestToFetchListOfBooksFromDatabase_WhenSendIncorrectUrlPath_ThenShouldReturnUrlNotFoundStatus() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        this.mockMvc.perform(post("/index/list"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenRequestToFetchListOfBooksFromDatabase_WhenSendIncorrectRequestBody_ThenShouldReturnMethodNotAllowedStatus() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreService.getAllBooks(0, 10)).thenReturn(booksList);
        this.mockMvc.perform(post("/books/0")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void givenRequestToGetCountOfBooksInDatabase_ShouldReturnCountOfBooksInDatabase() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreService.getCountOfBooks()).thenReturn(booksList.size());
        MvcResult mvcResult = this.mockMvc.perform(get("/books/count")).andReturn();
        Assert.assertEquals("1", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenRequestToGetCountOfBooksInDatabase_WhenGetCount_ShouldReturnStatusOk() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List booksList = new ArrayList();
        booksList.add(bookDetails);
        when(onlineBookStoreService.getCountOfBooks()).thenReturn(booksList.size());
        MvcResult mvcResult = this.mockMvc.perform(get("/books/count")).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void givenRequestToSearchBooks_WhenFound_ShouldReturnTrue() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List bookList = new ArrayList();
        bookList.add(bookDetails);
        Page<BookDetails> page = new PageImpl(bookList);
        when(onlineBookStoreService.searchBooks(any(), any())).thenReturn(page);
        MvcResult mvcResult = this.mockMvc.perform(get("/search/0/Mrutyunjay")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Mrutyunjay"));
    }

    @Test
    public void givenRequestToFilterBooksOnSearchedString_WhenFiltered_ShouldReturnBookDetails() throws Exception {
        bookDTO = new BookDTO("1000", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        bookDetails = new BookDetails(bookDTO);
        List bookList = new ArrayList();
        bookList.add(bookDetails);
        when(onlineBookStoreService.findAllBooks(any(), anyInt(), any())).thenReturn(bookList);
        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/sort/0/Mrutyunjay/LOW_TO_HIGH")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }
}