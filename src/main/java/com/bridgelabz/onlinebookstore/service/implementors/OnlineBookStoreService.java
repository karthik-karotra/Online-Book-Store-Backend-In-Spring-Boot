package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OnlineBookStoreService implements IOnlineBookStoreService {

    @Autowired
    private IOnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookDetails saveBook(BookDTO bookDTO) {
        BookDetails bookDetails = modelMapper.map(bookDTO, BookDetails.class);
        Optional<BookDetails> byIsbn = onlineBookStoreRepository.findByIsbn(bookDTO.getIsbn());
        Optional<BookDetails> byBookNameAndAuthorName = onlineBookStoreRepository.findByBookNameAndAuthorName(bookDTO.getBookName(), bookDTO.getAuthorName());
        if (byIsbn.isPresent()) {
            throw new OnlineBookStoreException("ISBN No Already Exists", OnlineBookStoreException.ExceptionType.ISBN_NO_ALREADY_EXISTS);
        } else if (byBookNameAndAuthorName.isPresent()) {
            throw new OnlineBookStoreException("Book And Author Name Already Exists", OnlineBookStoreException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS);
        }
        return onlineBookStoreRepository.save(bookDetails);
    }
}
