package com.cos.book.domain;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY) // 가짜디비로 테스트 Replace.NONE으로 하면 진짜 DB로 함.
@DataJpaTest //mock을 안띄우는 이유는 Repository들을 IoC에 등록함.
public class BookRepositoryUnitTest {
  // @Autowired
  // private BookRespository bookRespository;
}
