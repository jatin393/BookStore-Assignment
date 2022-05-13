package com.nagarro.bookStore.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.bookStore.model.BookStore;
import com.nagarro.bookStore.model.Media;
import com.nagarro.bookStore.service.BookService;

import io.restassured.RestAssured;
import springfox.documentation.swagger2.mappers.HttpAuthenticationSchemeFactory;

/**
 * @RestController It's a convenient annotation that combines @Controller
 *                 and @ResponseBody
 */
@RestController
@RequestMapping("/bookStore")
public class Controller {

	/**
	 * In Java, logging is an important feature that helps developers to trace out
	 * the errors. It provides the complete tracing information of the application.
	 * It records the critical failure if any occur in an application..
	 */
	Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private BookService service;

	@GetMapping("/{keyword}")
	public ResponseEntity<?> Search(@PathVariable("keyword") String keyword) {

		logger.debug("Request {}", keyword);
		List<BookStore> result = this.service.searchBook(keyword);
		if (result.size() == 0) {
			logger.warn("Invalid Input");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found !!");
		}
		// If Book Found
		logger.info("Data Found With Corresponding Keyword");
		logger.debug("Response {}", result);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getBooks")
	public ResponseEntity<?> getAllBooks() {
		// Get All Books
		List<BookStore> books = this.service.getAllBooks();
		if (books.size() <= 0) {
			logger.error("Data Not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Data Found");
		logger.debug("Response {}", books);
		return ResponseEntity.ok(books);
	}

	@PutMapping("/updateBook/{bId}")
	// Update Particular Book
	public BookStore updateBook(@RequestBody BookStore book, @PathVariable("bId") int bId) {
		BookStore books = this.service.updateBooks(book, bId);
		if (books == null) {
			logger.error("Current Id Doesn't Belongs To The Actual Data That Already Present");
			return null;
		}
		logger.info("Data Updated");
		logger.debug("Response {}", books);
		return books;

	}

	@GetMapping("/getBook/{bId}")
	// Get Particular Book By Using BookId
	public ResponseEntity<?> getBook(@PathVariable int bId) {
		BookStore result = this.service.getBook(bId);
		if (result == null) {
			logger.error("Data Not Found With The Corresponding BookId");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found !!");
		}
		logger.info("Data Found With The Corresponding BookId");
		logger.debug("Response {}", result);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/result")
	public ResponseEntity<?> Search(@RequestBody BookStore books) {

		// Search Result On The Basis Of Three Facts (Isbn,Author,Title)

		List<BookStore> result = this.service.searchDataByFact(books);
		if (result == null) {
			logger.info("Please Enter Correct Data To Fetch The Result");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
		}
		logger.info("Getting Result");
		logger.debug("Response {}", result);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/addBook")
	public BookStore addBooks(@Valid @RequestBody BookStore books) {
		// If I don't want Number Of Copies To Be Inserted.
		BookStore book = this.service.addBooksWithoutCopy(books);
		logger.info("Adding Data");
		logger.debug("Response {}", book);
		return book;
	}

	@PutMapping("/addBook/{quantity}")
	public BookStore addBooks(@RequestBody BookStore books, @PathVariable("quantity") int quantity) {
		// If I Add The Number Of Copies Of A Particular Book
		BookStore book = this.service.addBooksWithCopy(books, quantity);
		logger.info("Adding Data Having Multiple Copies");
		logger.debug("Response {}", book);
		return book;
	}

	@GetMapping("/callApi")
	// This media is used to call Json Api
	public ResponseEntity<?> getMedia(@RequestBody Media input) {
//		var url = "https://jsonplaceholder.typicode.com/posts";
//		var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
//		var client = HttpClient.newBuilder().build();
//		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		HttpResponse<String>jsonCall = response;
//		String result3 = response.body();
//		List<String> myList = new ArrayList<String>(Arrays.asList(result3.split(",")));
//		this.service.getMedia(myList, media);
//		System.out.println(myList);
//		System.out.println(response.body());
//		System.out.println(jsonCall);
//		return response.statusCode();
		return this.service.getMediaPost(input, logger);
	}

	// Created RestApi to Buying a Book
	@GetMapping("/purchase")
	public ResponseEntity<?> BookBuy(@RequestBody BookStore book) {

		return this.service.buyBook(book, logger);
	}

}
