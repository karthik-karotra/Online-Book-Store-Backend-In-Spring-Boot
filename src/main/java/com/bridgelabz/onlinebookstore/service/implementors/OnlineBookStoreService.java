package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OnlineBookStoreService implements IOnlineBookStoreService {

    @Autowired
    private IOnlineBookStoreRepository onlineBookStoreRepository;

    @Override
    public List<BookDetails> getAllBooks(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<BookDetails> bookList = onlineBookStoreRepository.findAll(paging);
        if (!bookList.hasContent()) {
            throw new OnlineBookStoreException("No Books Were Found On The Page", OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND);
        }
        return bookList.getContent();
    }

    @Override
    public Integer getCountOfBooks() {
        List list=onlineBookStoreRepository.findAll();
        if(list.size() == 0)
            throw new OnlineBookStoreException("No Books Were Found In Database", OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND);
        return list.size();
    }
}