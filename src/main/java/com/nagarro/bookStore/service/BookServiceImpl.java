package com.nagarro.bookStore.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nagarro.bookStore.bookDao.BooksDao;
import com.nagarro.bookStore.model.BookStore;
import com.nagarro.bookStore.model.Media;

import io.restassured.RestAssured;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BooksDao bookRepo;
	
	@Autowired
	private BookService service;

	/**
	 * Add books to the bookstore
	 * 
	 * @return the response of book added.
	 */
	@Override
	public BookStore addBook(BookStore b) {
		// Adding Book To The BookStore

		return this.bookRepo.save(b);

	}

	/**
	 * searchBook method finds the number of books based on the keyword
	 * 
	 * @return the list of available books
	 */
	@Override
	public List<BookStore> searchBook(String keyword) {

		List<BookStore> result = this.bookRepo.findByIsbnOrAuthorOrTitle(keyword, keyword, keyword);
		return result;
	}

	/**
	 * getAllBooks method find all the records present in our database.
	 * 
	 * @return the list of all the books
	 */
	@Override
	public List<BookStore> getAllBooks() {
		// List Of Get All Books

		List<BookStore> bookList = this.bookRepo.findAll();
		return bookList;

	}

	/**
	 * updateBooks method update a particular book based on their BookId If the book
	 * is not present, will add a new Entry of their book
	 * 
	 * @return the update status of a book
	 */
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

	/**
	 * getBook method find the book based on their bookId
	 * 
	 * @return the status of book, stated present or not.
	 */
	@Override
	public BookStore getBook(int bId) {
		// Find Particular Book Using BookId
		BookStore books = this.bookRepo.findBybId(bId);
		if (books == null) {
			return null;
		}
		return books;
	}

	/**
	 * searchBooks method is used to search the result based on their keyword
	 * Requires only one input as keyword
	 * 
	 * @return the matching records in any one of their attributes.
	 */
	@Override
	public List<BookStore> searchBooks(String keyword) {
		// To Find Books Based On Any Condition
		List<BookStore> result = this.bookRepo.findByIsbnOrAuthorOrTitle(keyword, keyword, keyword);
		return result;
	}

	/**
	 * searchDataByFact method is used to find the result that has to be matched
	 * with all the three local variables Isbn1,author1,title1 Require three
	 * arguments to find the result All three arguments has to be known to the user
	 * 
	 * @return the matching records based on their three inputs in the form of List.
	 */
	@Override
	public List<BookStore> searchDataByFact(BookStore books) {

		// To Find Books Based On Three Condition
		String Isbn1 = books.getIsbn();
		String author1 = books.getAuthor();
		String title1 = books.getTitle();

		List<BookStore> bookResult = this.bookRepo.findByIsbnAndAuthorAndTitle(Isbn1, author1, title1);
		return bookResult;
	}

	/**
	 * addBooksWithCopy method is used to post the multiple copies of a same book
	 * based on their Isbn. Also, add the number of copies or quantity to be added.
	 * Require two paramters books and quantity
	 * 
	 * @return the response of the result having statusCode .
	 */
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

	/**
	 * addBooksWithoutCopy method is used to add the multiple copies of a book based
	 * on their Isbn. Doesn't requires the quantity parameter Increment the value,
	 * if copies already existed
	 * 
	 * @return the response of Object that has to be saved in the database.
	 */
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

	@Override
	public void getMedia(List<String> myList, Media media) {
		// Fetching records from the list
		String title = media.getTitle();
		for (@SuppressWarnings("unused")
		String result : myList) {
			for (int i = 0; i < 4; i++) {
				if (title == media.getTitle()) {
					System.out.println(media);
				}
			}
		}
	}

	@Override
	public BookStore findTitle(String title) {
		BookStore result = this.bookRepo.findByTitle(title);
		if (result == null) {
			return null;
		}
		return result;

	}

	@Override
	public ResponseEntity<?> getMediaPost(Media input, Logger logger) {
		ArrayList<Media> arr = new ArrayList<>();
		logger.info("Calling Json Url");
		Media[] medias = RestAssured.get("https://jsonplaceholder.typicode.com/posts").as(Media[].class);
		arr.clear();
		int count = 0;
		String data = input.getTitle();
		for (int i = 0; i < medias.length; i++) {
			String title = medias[i].getTitle();
			String body = medias[i].getBody();
			if (title.contains(data) || body.contains(data)) {
				System.out.println("Present");
				arr.add(medias[i]);
				System.out.println(arr);
				count++;
			}
		}
		if (count == 0) {
			logger.error("Data Not Present Or Invalid Input");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found.");
		}
		logger.info("Data Found");
		return ResponseEntity.ok(arr);

	}

	@Override
	public ResponseEntity<?> buyBook(BookStore book, Logger logger) {
		// Creating ArrayList For Books That Are Invalid Or Not Present.
        // ArrayList<String> list = new ArrayList<>();
		String nf = "";
		int count = 0;
		int totalCost = 0;
		String str = book.getTitle();
		System.out.println(str);
		String result[] = str.split(",");
		logger.info("Fetching Result");
		for (String ans : result) {
			logger.debug(ans);
			BookStore getResult = this.service.findTitle(ans);
			if (getResult == null) {
				System.out.println("......The Book Author " + ans + " Is Not Valid......");
				nf += ans;
				count++;
				logger.warn("The Book Author Is not Valid");
				continue;
			} else {
				// To Find Price of the result to calculate the estimation cost.
				totalCost += getResult.getPrice() * getResult.getOrderQuantity();
			}
		}
		String st = "";
		if (count == 1) {
			st += count + " Book Is Not Found, Either Invalid Name Of Author Or Not Present In Db." + "\n"
					+ "Invalid Book Is " + nf;
		} else if (count > 1) {
			st += count + " Books Are Not Found, Either Invalid Name Of Author Or Not Present In Db." + "\n"
					+ "Invalid Books Are " + nf;
		}
		return ResponseEntity.status(HttpStatus.FOUND)
				.body("The Total Cost For The Book Purchase Is : " + totalCost + "\n" + st);
	}

}
