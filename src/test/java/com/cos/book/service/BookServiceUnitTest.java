package com.cos.book.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cos.book.domain.BookRespository;

//단위테스트
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
  @InjectMocks // BookService 객체가 만들어질때 @Mock으로 등록된 모든 애들을 주입받는다.
  private BookService bookService;
  @Mock
  private BookRespository bookRepository;
}
