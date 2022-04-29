package com.nagarro.bookStore.bookDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.bookStore.model.BookStore;

public interface BooksDao extends JpaRepository<BookStore, Integer> {

	public List<BookStore> findByIsbnOrAuthorOrTitle(String isbn, String author, String title);

	public BookStore findBybId(int bId);

	public List<BookStore> findByIsbnAndAuthorAndTitle(String isbn, String author, String title);

	public BookStore findByIsbn(String isbn);
}
