package com.nagarro.bookStore.IntegrationTesting;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.bookStore.bookDao.BooksDao;
import com.nagarro.bookStore.model.BookStore;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

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
	}

	@Test
	public void addBooksToBookStore() throws JsonProcessingException, Exception {
		BookStore books = new BookStore(5334, "3242455", "Universe Mystery", "Jatin", 405, 5);
		String url = "/bookStore/addBook";
		MvcResult mvcResult = mockMvc
				.perform(put(url).contentType("application/json").content(mapper.writeValueAsString(books)))
				.andExpect(status().isOk()).andDo(print()).andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println(response);
	}
}
