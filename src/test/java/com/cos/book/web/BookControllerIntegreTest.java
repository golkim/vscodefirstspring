package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// 통합테스트(모든 Bean들을 똑같이 테스트)
// WebEnvironment.MOCK 은 실제 톰켓이 아니라 다른콤켓으로 테스트
// WebEnvironment.RANDOM_PORT 실제 톰켓을 사용
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) //실제 톰켓올리는것이 아니라, 다른톰켓으로 테스트
public class BookControllerIntegreTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookRespository bookRespository;

  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  public void init() {
    entityManager
      .createNativeQuery("ALTER TABLE BOOK ALTER COLUMN id ReSTART WITH  1")
      .executeUpdate();
  }

  @Test
  public void save_테스트() throws Exception {
    //given
    Book book = new Book(null, "스프링따라하기", "코스");
    String content = new ObjectMapper().writeValueAsString(book);

    //when
    ResultActions resultAction = mockMvc.perform(
      post("/book")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(content)
        .accept(MediaType.APPLICATION_JSON_UTF8)
    );

    //then
    resultAction
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.title").value("스프링따라하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void findAll_테스트() throws Exception {
    //given
    List<Book> books = new ArrayList<>();
    books.add(new Book(null, "스프링부트 따라하기", "코스"));
    books.add(new Book(null, "스프링부트 따라하기2", "코스2"));
    bookRespository.saveAll(books);

    //when
    ResultActions resultAction = mockMvc.perform(
      get("/book").accept(MediaType.APPLICATION_JSON_UTF8)
    );

    //then
    resultAction
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", Matchers.hasSize(2)))
      .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void findById_테스트() throws Exception {
    //given
    Long id = 1L;
    List<Book> books = new ArrayList<>();
    books.add(new Book(null, "스프링부트 따라하기", "코스"));
    books.add(new Book(null, "스프링부트 따라하기2", "코스2"));
    bookRespository.saveAll(books);

    //when
    ResultActions resultAction = mockMvc.perform(
      get("/book/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8)
    );
    //then
    resultAction
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title").value("스프링부트 따라하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void update_테스트() throws Exception {
    //given
    List<Book> books = new ArrayList<>();
    books.add(new Book(null, "스프링부트 따라하기", "코스"));
    books.add(new Book(null, "스프링부트 따라하기2", "코스2"));
    bookRespository.saveAll(books);

    Long id = 2L;
    Book book = new Book(null, "씨뿔뿔따라하기", "코스");
    String content = new ObjectMapper().writeValueAsString(book);

    //when
    ResultActions resultAction = mockMvc.perform(
      put("/book/{id}", id)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(content)
        .accept(MediaType.APPLICATION_JSON_UTF8)
    );
    //then
    resultAction
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value("2"))
      .andExpect(jsonPath("$.title").value("씨뿔뿔따라하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void delete_테스트() throws Exception {
    //given
    List<Book> books = new ArrayList<>();
    books.add(new Book(null, "스프링부트 따라하기", "코스"));
    books.add(new Book(null, "스프링부트 따라하기2", "코스2"));
    bookRespository.saveAll(books);

    Long id = 1L;

    //when
    ResultActions resultAction = mockMvc.perform(
      delete("/book/{id}", id).accept(MediaType.TEXT_PLAIN)
    );
    //then
    resultAction
      .andExpect(status().isOk())
      .andDo(MockMvcResultHandlers.print());
    MvcResult requestResult = resultAction.andReturn();
    String result = requestResult.getResponse().getContentAsString();
    assertEquals("ok", result);
  }
}
