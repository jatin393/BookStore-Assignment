package com.nagarro.bookStore.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class BookStore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bId;
	private String isbn;
	private String title;
	private String author;
	private double price;
	private int orderQuantity;

	public BookStore(int bId, String isbn, String title, String author, double price, int orderQuantity) {
		super();
		this.bId = bId;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.orderQuantity = orderQuantity;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BookStore() {
		super();

	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BookStore [bId=" + bId + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", price="
				+ price + ", orderQuantity=" + orderQuantity + "]";
	}

}
