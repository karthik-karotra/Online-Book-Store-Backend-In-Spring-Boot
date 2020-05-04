package com.bridgelabz.onlinebookstore.service.implementors;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
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
    IOnlineBookStoreRepository onlineBookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookDetails addBook(BookDTO bookDTO) {
        BookDetails bookDetails = modelMapper.map(bookDTO, BookDetails.class);
        Optional<BookDetails> byIsbn = onlineBookStoreRepository.findByIsbn(bookDTO.getIsbn());
        if(byIsbn.isPresent()){
            bookDetails.setQuantity(byIsbn.get().getQuantity()+bookDTO.getQuantity());
            bookDetails.setBookPrice(bookDTO.getBookPrice());
            bookDetails.setId(byIsbn.get().getId());
        }
        return onlineBookStoreRepository.save(bookDetails);
    }
}
