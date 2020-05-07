package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OnlineBookStoreService implements IOnlineBookStoreService {

    @Override
    public List<BookDetails> getAllBooks() {
        return null;
    }
}
