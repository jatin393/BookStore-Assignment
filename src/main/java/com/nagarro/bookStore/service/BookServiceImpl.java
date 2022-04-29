package com.nagarro.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.bookStore.bookDao.BooksDao;
import com.nagarro.bookStore.model.BookStore;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BooksDao bookRepo;

	@Override
	public BookStore addBook(BookStore b) {
		// Adding Book To The BookStore

		return this.bookRepo.save(b);

	}

	@Override
	public List<BookStore> searchBook(String keyword) {
		// Search Book Based On Keyword

		List<BookStore> result = this.bookRepo.findByIsbnOrAuthorOrTitle(keyword, keyword, keyword);
		return result;
	}

	@Override
	public List<BookStore> getAllBooks() {
		// List Of Get All Books

		List<BookStore> bookList = this.bookRepo.findAll();
		return bookList;

	}

	@Override
	public BookStore updateBooks(BookStore book, int bId) {
		// To Update Any Books
		BookStore books = this.bookRepo.findBybId(bId);
		if (books == null) {
			return null;
		}
		book.setbId(bId);
		BookStore bk = this.bookRepo.save(book);
		return bk;
	}

	@Override
	public BookStore getBook(int bId) {
		// Find Particular Book Using BookId
		BookStore books = this.bookRepo.findBybId(bId);
		if (books == null) {
			return null;
		}
		return books;
	}

	@Override
	public List<BookStore> searchBooks(String keyword) {
		// To Find Books Based On Any Condition
		List<BookStore> result = this.bookRepo.findByIsbnOrAuthorOrTitle(keyword, keyword, keyword);
		return result;
	}

	@Override
	public List<BookStore> searchDataByFact(BookStore books) {

		// To Find Books Based On Three Condition
		String Isbn1 = books.getIsbn();
		String author1 = books.getAuthor();
		String title1 = books.getTitle();

		List<BookStore> bookResult = this.bookRepo.findByIsbnAndAuthorAndTitle(Isbn1, author1, title1);
		return bookResult;
	}

	@Override
	public BookStore addBooksWithCopy(BookStore books, int quantity) {
	
		BookStore findIsbn = this.bookRepo.findByIsbn(books.getIsbn());
		if (findIsbn == null) {
			books.setOrderQuantity(quantity);
		} else {
			// It Means The Isbn Is Already Present
			int count = findIsbn.getOrderQuantity();
			int copies = count + quantity;
			findIsbn.setOrderQuantity(copies);
			return this.bookRepo.save(findIsbn);
		}
		return this.bookRepo.save(books);
	}

	@Override
	public BookStore addBooksWithoutCopy(BookStore books) {
		BookStore findIsbn = this.bookRepo.findByIsbn(books.getIsbn());
		if (findIsbn == null) {
			books.setOrderQuantity(1);
		} else {
			// It Means The Isbn Is Already Present
			int count = findIsbn.getOrderQuantity();
			findIsbn.setOrderQuantity(count + 1);
			return this.bookRepo.save(findIsbn);
		}
		return this.bookRepo.save(books);
	}

}
