package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @GetMapping("/books/{pageNo}")
    public ResponseEntity<ResponseDTO> getBook(@PathVariable Integer pageNo) {
        List<BookDetails> bookDetailsList = onlineBookStoreService.getAllBooks(pageNo, 12);
        ResponseDTO responseDTO = new ResponseDTO(bookDetailsList, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/books/count")
    public ResponseEntity<ResponseDTO> getCount() {
        Integer countOfBooks = onlineBookStoreService.getCountOfBooks();
        ResponseDTO responseDTO = new ResponseDTO(countOfBooks, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/books/sort/{pageNo}/{searchText}/{filterAttributes}")
    public ResponseEntity<ResponseDTO> sort(@PathVariable String searchText, @PathVariable int pageNo, @PathVariable FilterAttributes filterAttributes) {
        Page<BookDetails> booksPerPage = onlineBookStoreService.findAllBooks(searchText, (pageNo - 1), filterAttributes);
        ResponseDTO responseDTO = new ResponseDTO(booksPerPage, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/book/{fileName:.+}")
    public ResponseEntity downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = onlineBookStoreService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}