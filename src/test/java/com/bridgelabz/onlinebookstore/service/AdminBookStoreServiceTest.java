package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.AdminRepository;
import com.bridgelabz.onlinebookstore.service.implementations.AdminBookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import java.io.File;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminBookStoreServiceTest {

    @MockBean
    AdminRepository onlineBookStoreRepository;

    @Autowired
    AdminBookStoreService adminBookStoreService;

    @MockBean
    ApplicationProperties applicationProperties;

    BookDTO bookDTO;

    @Test
    public void givenBookDetailsToAdd_WhenGetResponse_ShouldReturnBookDetails() {
        bookDTO = new BookDTO("9876543210", "Half Girlfriend", "Chetan Bhagat", 400.0, 10, "Devotional", "book image", 2002);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(onlineBookStoreRepository.save(any())).thenReturn(bookDetails);
        String message = "ADDED SUCCESSFULLY";
        String saveBook = adminBookStoreService.saveBook(bookDTO);
        Assert.assertEquals(message, saveBook);
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameIsbnNumber_ShouldThrowException() {
        try {
            bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(onlineBookStoreRepository.findByIsbn("1234567890")).thenReturn(Optional.of(bookDetails));
            adminBookStoreService.saveBook(bookDTO);
        } catch (AdminException e) {
            Assert.assertEquals(AdminException.ExceptionType.ISBN_NO_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenBookDetailsToAdd_WhenAddedSameBookAndAuthorName_ShouldThrowException() {
        try {
            bookDTO = new BookDTO("1234567890", "Mrutyunjay", "Shivaji Sawant", 400.0, 10, "Devotional", "book image", 2002);
            BookDetails bookDetails = new BookDetails(bookDTO);
            when(onlineBookStoreRepository.findByBookNameAndAuthorName("Mrutyunjay", "Shivaji Sawant")).thenReturn(Optional.of(bookDetails));
            adminBookStoreService.saveBook(bookDTO);
        } catch (AdminException e) {
            Assert.assertEquals(AdminException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS, e.type);
        }
    }

    @Test
    void givenImageToAddAtServerSide_WhenStoredInDirectory_ShouldReturnTrue() {
        when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.jpg", "text/plain", "some xml".getBytes());
        adminBookStoreService.uploadImage(multipartFile);
        String path = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        Assert.assertTrue(new File(path).exists());
    }

    @Test
    void givenImageToAddAtServerSide_WhenNotStoredInDirectory_ShouldReturnFalse() {
        when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.jpg", "text/plain", "some xml".getBytes());
        adminBookStoreService.uploadImage(multipartFile);
        String path = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        Assert.assertFalse(new File(path + "abc").exists());
    }

    @Test
    void givenWrongImageFileFormat_ShouldThrowException() {
        try {
            when(applicationProperties.getUploadDirectory()).thenReturn("/src/main/resources/Images/");
            MockMultipartFile multipartFile = new MockMultipartFile("data", "1-world-best-bf.pdf", "text/plain", "some xml".getBytes());
            String path = adminBookStoreService.uploadImage(multipartFile);
        } catch (AdminException ex) {
            Assert.assertEquals(AdminException.ExceptionType.INCOMPATIBLE_TYPE, ex.type);
        }
    }
}