package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.repository.IOnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OnlineBookStoreService implements IOnlineBookStoreService {

    @Autowired
    private IOnlineBookStoreRepository onlineBookStoreRepository;

    @Override
    public List<BookDetails> getAllBooks() {
        List<BookDetails> bookList=onlineBookStoreRepository.findAll();
        if(bookList.size()==0) {
            throw new OnlineBookStoreException("No Books Were Found In Database", OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND);
        }
        return bookList;
    }

    @Override
    public Integer getCountOfBooks() {
        List list=onlineBookStoreRepository.findAll();
        return list.size();
    }
}
