package com.nagarro.bookStore.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

import com.nagarro.bookStore.model.BookStore;
import com.nagarro.bookStore.model.Media;

public interface BookService {

	public BookStore addBook(BookStore b);

	public List<BookStore> searchBook(String keyword);

	public List<BookStore> getAllBooks();

	public List<BookStore> searchBooks(String keyword);

	public BookStore updateBooks(BookStore book, int bId);

	public BookStore getBook(int bId);

	public List<BookStore> searchDataByFact(BookStore books);

	public BookStore addBooksWithCopy(BookStore books, int quantity);

	public BookStore addBooksWithoutCopy(BookStore books);

	public void getMedia(List<String> myList, Media media);

	public BookStore findTitle(String title);

	public ResponseEntity<?> getMediaPost(Media input, Logger logger);

	public ResponseEntity<?> buyBook(BookStore book, Logger logger);

}
