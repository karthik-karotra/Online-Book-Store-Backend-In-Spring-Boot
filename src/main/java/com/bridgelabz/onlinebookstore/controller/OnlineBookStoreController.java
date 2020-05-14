package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OnlineBookStoreController {

    @Autowired
    OnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @GetMapping("/books/{pageNo}")
    public ResponseEntity<ResponseDTO> getBook(@PathVariable Integer pageNo) {
        List<BookDetails> bookDetailsList = onlineBookStoreService.getAllBooks(pageNo, 12);
        ResponseDTO responseDTO = new ResponseDTO(bookDetailsList, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/books/count")
    public Integer getCount() {
        return onlineBookStoreService.getCountOfBooks();
    }

    @GetMapping("/search/{pageNo}/{searchText}")
    public ResponseEntity<Page<BookDetails>> findAll(@PathVariable Integer pageNo, @PathVariable String searchText) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return new ResponseEntity<>(onlineBookStoreService.searchBooks(pageable, searchText), HttpStatus.OK);
    }

    @GetMapping("order/{pageNumber}/{sortBy}/{sortDirection}")
    public ResponseEntity<Page<BookDetails>> sort(@PathVariable int pageNumber, @PathVariable String sortBy, @PathVariable String sortDirection) {

        Page<BookDetails> page = onlineBookStoreService.sortByAttribute(PageRequest.of(
                pageNumber, 12,
                sortDirection.equalsIgnoreCase("ascending") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
                )
        );
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/sort/{pageNo}/{searchText}/{filterAttributes}")
    public List<BookDetails> sort(@PathVariable String searchText, @PathVariable int pageNo, @PathVariable FilterAttributes filterAttributes) {
       return onlineBookStoreService.findAllBooks(searchText,pageNo,filterAttributes);
    }
}