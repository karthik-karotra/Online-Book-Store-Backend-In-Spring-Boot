package com.bridgelabz.onlinebookstore.filterenums;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum FilterAttributes {
    LOW_TO_HIGH {
        @Override
        public List<BookDetails> getSortedData(List<BookDetails> sortedBookList) {
            return sortedBookList.stream()
                    .sorted(Comparator.comparing(bookDetails -> bookDetails.bookPrice))
                    .collect(Collectors.toList());
        }
    },
    HIGH_TO_LOW {
        @Override
        public List<BookDetails> getSortedData(List<BookDetails> sortedBookList) {
            List<BookDetails> allBooks = sortedBookList.stream()
                    .sorted(Comparator.comparing(bookDetails -> bookDetails.bookPrice))
                    .collect(Collectors.toList());
            Collections.reverse(allBooks);
            return allBooks;
        }
    },
    NEWEST_ARRIVALS {
        @Override
        public List<BookDetails> getSortedData(List<BookDetails> sortedBookList) {
            List<BookDetails> allBooks = sortedBookList.stream()
                    .sorted(Comparator.comparing(bookDetails -> bookDetails.publishingYear))
                    .collect(Collectors.toList());
            Collections.reverse(allBooks);
            return allBooks;
        }
    };

    public abstract List<BookDetails> getSortedData(List<BookDetails> sortedBookList);
}
