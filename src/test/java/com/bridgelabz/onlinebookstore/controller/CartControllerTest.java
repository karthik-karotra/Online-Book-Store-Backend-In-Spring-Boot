package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.service.ICartService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICartService cartService;

    @MockBean
    public ApplicationProperties applicationProperties;

    Gson gson = new Gson();

    @Test
    public void givenBookDetailsAndCartDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectMessage() throws Exception {
        String message = "Book Successfully Added To Cart";
        String jsonString = gson.toJson(message);
        ResponseDTO responseDTO = new ResponseDTO(message);
        String jsonResponseDTO = gson.toJson(responseDTO);
        when(cartService.saveBooksToCart(anyInt(), anyInt(), any())).thenReturn(message);
        this.mockMvc.perform(post("/cart/2/1")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseDTO));
    }

    @Test
    public void givenBookDetailsAndCartDetailsToAddInDatabase_WhenAdded_ThenReturnCorrectStatus() throws Exception {
        String message = "Book Successfully Added To Cart";
        String jsonString = gson.toJson(message);
        when(cartService.saveBooksToCart(anyInt(), anyInt(), any())).thenReturn(message);
        this.mockMvc.perform(post("/cart/2/1")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestToFetchListOfBookDetailsFromCart_ShouldReturnListOfBookDetailsInCart() throws Exception {
        List<BookCart> bookList = new ArrayList();
        BookDTO bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookCart bookCart = new BookCart(bookDetails, 3);
        bookList.add(bookCart);
        String jsonDto = gson.toJson(bookList);
        when(cartService.getAllBooks(any())).thenReturn(bookList);
        MvcResult mvcResult = this.mockMvc.perform(get("/cart")
                .content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Mrutyunjay"));
    }

    @Test
    public void givenRequestToUpdateQuantityOfBookInCart_ShouldUpdateQuantityInCart() throws Exception {
        String message = "Cart Updated Successfully";
        String jsonString = gson.toJson(message);
        ResponseDTO responseDTO = new ResponseDTO(message);
        String jsonResponseDTO = gson.toJson(responseDTO);
        when(cartService.updateQuantity(anyInt(), anyInt(), any())).thenReturn(message);
        this.mockMvc.perform(put("/cart/1/2")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseDTO));
    }

    @Test
    public void givenRequestToDeleteBookFromCart_ShouldDeleteBookFromCart() throws Exception {
        String message = "Deleted Successfully";
        String jsonString = gson.toJson(message);
        ResponseDTO responseDTO = new ResponseDTO(message);
        String jsonResponseDTO = gson.toJson(responseDTO);
        when(cartService.deleteBookFromCart(anyInt(), any())).thenReturn(message);
        this.mockMvc.perform(delete("/cart/1")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponseDTO));
    }
}

