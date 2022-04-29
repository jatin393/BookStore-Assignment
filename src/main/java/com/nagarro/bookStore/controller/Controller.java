package com.nagarro.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.bookStore.model.BookStore;
import com.nagarro.bookStore.service.BookService;

@RestController
@RequestMapping("/bookStore")
public class Controller {

	@Autowired
	private BookService service;

	@PostMapping("/book")
	public BookStore addBook(@RequestBody BookStore b) {
		return this.service.addBook(b);
	}

	@GetMapping("/{keyword}")
	public ResponseEntity<?> Search(@PathVariable("keyword") String keyword) {

		List<BookStore> result = this.service.searchBook(keyword);
		if (result.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found !!");
		}
		// If Book Found
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getBooks")
	public ResponseEntity<?> getAllBooks() {
		// Get All Books
		List<BookStore> books = this.service.getAllBooks();
		if (books.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(books);
	}

	@PutMapping("/updateBook/{bId}")
	// Update Particular Book
	public BookStore updateBook(@RequestBody BookStore book, @PathVariable("bId") int bId) {
		return this.service.updateBooks(book, bId);
	}

	@GetMapping("/getBook/{bId}")
	// Get Particular Book By Using BookId
	public ResponseEntity<?> getBook(@PathVariable int bId) {
		BookStore result = this.service.getBook(bId);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found !!");
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/result")
	public ResponseEntity<?> Search(@RequestBody BookStore books) {
        
		// Search Result On The Basis Of Three Facts (Isbn,Author,Title)
		
		List<BookStore> result = this.service.searchDataByFact(books);
		if(result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
		}
		return ResponseEntity.ok(result);
	}
}
