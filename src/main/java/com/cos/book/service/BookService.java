package com.cos.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.BookRespository;

import lombok.RequiredArgsConstructor;
import com.cos.book.domain.Book;

@RequiredArgsConstructor
@Service
public class BookService {
  private final BookRespository bookRespository;
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
    bookRespository.deleteById(id);
    return "ok";
  }
}
