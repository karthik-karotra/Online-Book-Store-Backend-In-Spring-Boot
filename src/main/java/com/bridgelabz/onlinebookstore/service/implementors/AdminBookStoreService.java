package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IAdminRepository;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminBookStoreService implements IAdminBookStoreService {

    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public BookDetails saveBook(BookDTO bookDTO) {
        BookDetails bookDetails = new BookDetails(bookDTO);
        Optional<BookDetails> byIsbn = adminRepository.findByIsbn(bookDTO.isbn);
        Optional<BookDetails> byBookNameAndAuthorName = adminRepository.findByBookNameAndAuthorName(bookDTO.bookName, bookDTO.authorName);
        if (byIsbn.isPresent()) {
            throw new OnlineBookStoreException("ISBN No Already Exists", OnlineBookStoreException.ExceptionType.ISBN_NO_ALREADY_EXISTS);
        } else if (byBookNameAndAuthorName.isPresent()) {
            throw new OnlineBookStoreException("Book And Author Name Already Exists", OnlineBookStoreException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS);
        }
        return adminRepository.save(bookDetails);
    }
}
