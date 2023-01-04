package com.cos.book.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
  private final BookRespository bookRespository;
  private final Logger LOGGER = LoggerFactory.getLogger(BookService.class.getName());
  @Transactional
  public Book 저장하기(Book book){
    return bookRespository.save(book);
  }
  @Transactional(readOnly = true)
  public Book 한건가져오기(Long id){
    return bookRespository.findById(id)
    .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
  }
  @Transactional(readOnly = true)
  public List<Book> 모두가져오기(){
    return bookRespository.findAll();
  }
  @Transactional
  public Book 수정하기(Long id, Book book){
    Book bookEntity = bookRespository.findById(id)
    .orElseThrow(()-> new IllegalArgumentException("id를 확인해주세요!!"));
    bookEntity.setTitle(book.getTitle());
    bookEntity.setAuthor(book.getAuthor());
    return bookEntity;
  }
  @Transactional
  public String 삭제하기(Long id){
	if(bookRespository.existsById(id)) {
		bookRespository.deleteById(id);
	}else {
		LOGGER.error("=========없는 아이디 삭제===========");
	}
    return "ok";
  }
}
