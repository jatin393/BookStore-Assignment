package com.nagarro.bookStore.Test;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.bookStore.bookDao.BooksDao;
import com.nagarro.bookStore.controller.Controller;
import com.nagarro.bookStore.model.BookStore;
import com.nagarro.bookStore.service.BookService;

@WebMvcTest(Controller.class)
public class BookStoreControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private BookService service;
	@MockBean
	private BooksDao repo;

	private static List<BookStore> books = new ArrayList<>();

	@Test
	public void testGetAllBooks() throws Exception {
		books.add(new BookStore(1231, "6535325", "The Great Man", "King Luther", 560, 10));
		books.add(new BookStore(1232, "3565373", "Animal Kingdom", "Shaba Kher", 262, 40));
		books.add(new BookStore(1235, "1536323", "Virtual World", "Akash Chaudhary", 700, 2));
		books.add(new BookStore(1251, "9585323", "The Giant Universe", "Rohit Mathur", 650, 10));
		books.add(new BookStore(1237, "8545323", "Incredible India", "Mahesh Singh", 890, 60));
		Mockito.when(this.service.getAllBooks()).thenReturn(books);
		String url = "/bookStore/getBooks";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andDo(print()).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();
		System.out.println(jsonResponse);
	}

	@Test
	public void addBooksToBookStore() throws JsonProcessingException, Exception {
		BookStore books = new BookStore(5334, "3242455", "Universe Mystery", "Jatin", 405, 5);
		BookStore books1 = new BookStore(43422, "424244444", "Jurassic Park", "Neeraj Singh", 432, 20);
		Mockito.when(this.service.addBook(books)).thenReturn(books1);
		String url = "/bookStore/addBook";
		mockMvc.perform(put(url).contentType("application/json").content(mapper.writeValueAsString(books)))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void TestKeyword() throws Exception {
		String keyword = "A";
		Mockito.when(this.service.searchBook(keyword)).thenReturn(books);
		String url = "/bookStore/keyword";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();
		System.out.println(jsonResponse);
	}

	@Test
	public void AuthorAndTitleNotBlank() {
		BookStore books = new BookStore();
		String url = "/bookStore/addBook";
		try {
			mockMvc.perform(put(url).contentType("application/json").content(mapper.writeValueAsString(books)))
					.andExpect(status().isBadRequest()).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Mockito.verify(repo, times(0)).save(books);
	}

	@Test
	public void updateBooksToBookStore() throws JsonProcessingException, Exception {
		BookStore books = new BookStore(5334, "3242455", "Universe Mystery", "Jatin", 405, 5);
		BookStore books1 = new BookStore(5334, "3242455", "Universe Mystery", "Jatin", 405, 5);
		Mockito.when(this.service.addBook(books)).thenReturn(books1);
		String url = "/bookStore/addBook";
		mockMvc.perform(put(url).contentType("application/json").content(mapper.writeValueAsString(books)))
				.andExpect(status().isOk()).andExpect(content().string("1")).andDo(print());
	}

}
