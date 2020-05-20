package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.OnlineBookStoreRepository;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.toIntExact;

@Service
public class OnlineBookStoreService implements IOnlineBookStoreService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private OnlineBookStoreRepository onlineBookStoreRepository;

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
        List bookDetailsList = onlineBookStoreRepository.findAll();
        return bookDetailsList.size();
    }

    @Override
    public Page<BookDetails> findAllBooks(String searchText, int pageNo, FilterAttributes filterAttributes) {
        if (searchText.trim().length() == 0)
            searchText = "";
        List<BookDetails> allBooks = onlineBookStoreRepository.findAllBooks(searchText);
        if (allBooks.size() == 0) {
            throw new OnlineBookStoreException("No Books For Searched String Were Found", OnlineBookStoreException.ExceptionType.NO_BOOK_FOUND);
        }
        List<BookDetails> booksInSortedFormat = filterAttributes.getSortedData(allBooks);
        int totalBooks = booksInSortedFormat.size();
        PageRequest pageRequest = PageRequest.of(pageNo, 12);
        int startLimit = toIntExact(pageRequest.getOffset());
        int endLimit = Math.min((startLimit + pageRequest.getPageSize()), totalBooks);
        List<BookDetails> booksOnAPage = new ArrayList<>();
        if (startLimit <= endLimit) {
            booksOnAPage = booksInSortedFormat.subList(startLimit, endLimit);
        }
        return new PageImpl<>(booksOnAPage, pageRequest, booksInSortedFormat.size());
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            String fileBasePath = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
            Path path = Paths.get(fileBasePath + fileName);
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new OnlineBookStoreException("Image File Not Found", OnlineBookStoreException.ExceptionType.FILE_NOT_FOUND);
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new OnlineBookStoreException("Image File Not Found", OnlineBookStoreException.ExceptionType.FILE_NOT_FOUND);
        }
    }
}