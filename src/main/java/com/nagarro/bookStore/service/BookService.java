package com.nagarro.bookStore.service;

import java.util.List;

import com.nagarro.bookStore.model.BookStore;

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

}
