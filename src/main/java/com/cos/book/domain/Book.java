package com.cos.book.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String author;
}
