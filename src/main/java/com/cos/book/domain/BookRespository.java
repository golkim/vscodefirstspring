package com.cos.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRespository extends JpaRepository<Book, Long>{
  
}
