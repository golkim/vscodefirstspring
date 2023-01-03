package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
// 단위테스트(Controller 관련 로직만 테스트)
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean // IoC환경에 bean 등록됨.
  private BookService bookService;

  @Test
  public void save_테스트() throws Exception {
    //given(테스트를 위한 준비)
    Book book = new Book(null, "스프링따라하기", "코스");
    String content = new ObjectMapper().writeValueAsString(book);
    log.info(content);
    when(bookService.저장하기(book))
      .thenReturn(new Book(1L, "스프링따라하기", "코스"));
    //when
    ResultActions resultAction = mockMvc.perform(
      post("/book")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
        .accept(MediaType.APPLICATION_JSON)
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
    books.add(new Book(1L, "스프링부트 따라하기", "코스"));
    books.add(new Book(2L, "스프링부트 따라하기2", "코스2"));
    when(bookService.모두가져오기()).thenReturn(books);

    //when
    ResultActions resultAction = mockMvc.perform(
      get("/book").accept(MediaType.APPLICATION_JSON)
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
    when(bookService.한건가져오기(id))
      .thenReturn(new Book(1L, "자바공부하기", "쌀쌀"));
    //when
    ResultActions resultAction = mockMvc.perform(
      get("/book/{id}", id).accept(MediaType.APPLICATION_JSON)
    );
    //then
    resultAction
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title").value("자바공부하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void update_테스트() throws Exception {
    //given
    Long id = 1L;
    Book book = new Book(null, "씨뿔뿔따라하기", "코스");
    String content = new ObjectMapper().writeValueAsString(book);
    when(bookService.수정하기(id, book))
      .thenReturn(new Book(1L, "씨뿔뿔따라하기", "코스"));
    //when
    ResultActions resultAction = mockMvc.perform(
      put("/book/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content)
        .accept(MediaType.APPLICATION_JSON)
    );
    //then
    resultAction
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.title").value("씨뿔뿔따라하기"))
      .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void delete_테스트() throws Exception {
    //given
    Long id = 1L;

    when(bookService.삭제하기(id)).thenReturn("ok");
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
