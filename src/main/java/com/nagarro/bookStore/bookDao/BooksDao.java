package com.nagarro.bookStore.bookDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.bookStore.model.BookStore;

public interface BooksDao extends JpaRepository<BookStore, Integer> {
	/**
	 * @param isbn   indicates a unique number or code for each book.
	 * @param author is the writer of a particular book.
	 * @param title  shows the highlight of a book.
	 * @return
	 */
	public List<BookStore> findByIsbnOrAuthorOrTitle(String isbn, String author, String title);

	public BookStore findBybId(int bId);

	public List<BookStore> findByIsbnAndAuthorAndTitle(String isbn, String author, String title);

	public BookStore findByIsbn(String isbn);

	public BookStore findByTitle(String title);
}
