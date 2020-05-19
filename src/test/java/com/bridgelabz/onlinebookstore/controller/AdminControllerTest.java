package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdminBookStoreService adminBookStoreService;

    @MockBean
    public ApplicationProperties applicationProperties;

    Gson gson = new Gson();
    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectMessage() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        String toJson = gson.toJson(bookDTO);
        String message = "Book Added Successfully";
        when(adminBookStoreService.saveBook(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/admin/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Book Added Successfully"));
    }

    @Test
    public void givenBookDetailsToAddInDatabase_WhenDataInvalid_ShouldThrowException() throws Exception {

        bookDTO = new BookDTO("1234", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        String toJson = gson.toJson(bookDTO);
        when(adminBookStoreService.saveBook(any())).thenThrow(new OnlineBookStoreException("Invalid Data!!!!! Please Enter Valid Data", OnlineBookStoreException.ExceptionType.INVALID_DATA));
        MvcResult mvcResult = this.mockMvc.perform(post("/admin/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Invalid Data!!!!! Please Enter Valid Data"));
    }

    @Test
    public void givenBookDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectStatus() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        String toJson = gson.toJson(bookDTO);
        this.mockMvc.perform(post("/admin/book").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void givenIncorrectUrlPath_ShouldReturnURLNotFound() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String toJson = gson.toJson(bookDetails);
        this.mockMvc.perform(post("/index/addvirtualbook").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void givenDataWithoutJsonConversion_ShouldReturn400StatusCode() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        MvcResult mvcResult = this.mockMvc.perform(post("/admin/book").content(String.valueOf(bookDTO))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void givenContentTypeOfAnotherType_ShouldReturnUnsupporteMediaType() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String toJson = gson.toJson(bookDetails);
        this.mockMvc.perform(post("/admin/book").content(toJson)
                .contentType(MediaType.APPLICATION_ATOM_XML_VALUE)).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void givenIncorrectRequestBody_ShouldReturnMethodNotAllowed() throws Exception {
        bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String toJson = gson.toJson(bookDetails);
        this.mockMvc.perform(get("/admin/book").content(toJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void givenImageAsMultipartFile_ShouldReturnCorrectImageURL() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("file", "abc.jpg",
                "image/jpg", "Image".getBytes());
        MvcResult result = this.mockMvc.perform(multipart("/admin/book/image")
                .file(imageFile))
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
    }
}
