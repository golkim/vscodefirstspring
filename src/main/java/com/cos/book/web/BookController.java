package com.cos.book.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@RestController
public class BookController {
  @GetMapping("/")
  public ResponseEntity<?> findAll() {
      return new ResponseEntity<String>("ok", HttpStatus.OK);
  }
  
}
