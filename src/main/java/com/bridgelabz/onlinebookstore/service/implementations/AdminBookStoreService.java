package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.AdminRepository;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class AdminBookStoreService implements IAdminBookStoreService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public String saveBook(BookDTO bookDTO) {
        BookDetails bookDetails = new BookDetails(bookDTO);
        Optional<BookDetails> optionalBookDetails = adminRepository.findByIsbn(bookDTO.isbn);
        Optional<BookDetails> optionalBookDetails1 = adminRepository.findByBookNameAndAuthorName(bookDTO.bookName, bookDTO.authorName);
        if (optionalBookDetails.isPresent()) {
            throw new AdminException("ISBN No Already Exists", AdminException.ExceptionType.ISBN_NO_ALREADY_EXISTS);
        }
        if (optionalBookDetails1.isPresent()) {
            throw new AdminException("Book And Author Name Already Exists", AdminException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS);
        }
        adminRepository.save(bookDetails);
        return "ADDED SUCCESSFULLY";
    }

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileBasePath = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
            throw new AdminException("Only Image Files Can Be Uploaded", AdminException.ExceptionType.INCOMPATIBLE_TYPE);
        }
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageResponse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("book/")
                .path(fileName)
                .toUriString();
        return imageResponse;
    }
}
