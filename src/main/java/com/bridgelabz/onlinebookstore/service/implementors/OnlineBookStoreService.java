package com.bridgelabz.onlinebookstore.service.implementors;

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
        return onlineBookStoreRepository.findAll();
    }

    @Override
    public Integer getCountOfBooks() {
        List list=onlineBookStoreRepository.findAll();
        return list.size();
    }
}
