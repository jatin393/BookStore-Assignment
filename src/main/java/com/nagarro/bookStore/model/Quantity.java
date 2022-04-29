package com.nagarro.bookStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BookQuantity")
public class Quantity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int qId;
	@ManyToOne
	private BookStore books;
	private int quantity;
	private String isbn;

	public Quantity() {
		super();
	}

	public Quantity(int qId, BookStore books, int quantity, String isbn) {
		this.qId = qId;
		this.books = books;
		this.quantity = quantity;
		this.isbn = isbn;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getqId() {
		return qId;
	}

	public void setqId(int qId) {
		this.qId = qId;
	}

	public BookStore getBooks() {
		return books;
	}

	public void setBooks(BookStore books) {
		this.books = books;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Quantity [qId=" + qId + ", books=" + books + ", quantity=" + quantity + ", isbn=" + isbn + "]";
	}

}