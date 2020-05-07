package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdminBookStoreService adminBookStoreService;

    Gson gson = new Gson();

    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectStatus() throws Exception {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "bfjadlbfajlal", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String jsonDto = gson.toJson(bookDetails);
        ResponseDTO responseDTO = new ResponseDTO("ADDED SUCCESSFULLY", bookDetails);
        String jsonResponseDto = gson.toJson(responseDTO);
        when(adminBookStoreService.saveBook(any())).thenReturn(bookDetails);
        mockMvc.perform(post("/admin/book")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponseDto));
    }

    @Test
    public void givenIncorrectUrlPath_ShouldReturnURLNotFound() throws Exception {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book_image", 2002);
        String jsonDto = gson.toJson(bookDTO);
        this.mockMvc.perform(post("/index/addvirtualbook").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void givenDataWithoutJsonConversion_ShouldReturn400StatusCode() throws Exception {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book_image", 2002);
        MvcResult mvcResult = this.mockMvc.perform(post("/admin/book").content(String.valueOf(bookDTO))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void givenContentTypeOfAnotherType_ShouldReturnUnsupporteMediaType() throws Exception {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book_image", 2002);
        String jsonDto = gson.toJson(bookDTO);
        this.mockMvc.perform(post("/admin/book").content(jsonDto)
                .contentType(MediaType.APPLICATION_ATOM_XML_VALUE)).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void givenIncorrectRequestBody_ShouldReturnMethodNotAllowed() throws Exception {
        bookDTO = new BookDTO(1000, "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book_image", 2002);
        String jsonDto = gson.toJson(bookDTO);
        this.mockMvc.perform(get("/admin/book").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());
    }
}
