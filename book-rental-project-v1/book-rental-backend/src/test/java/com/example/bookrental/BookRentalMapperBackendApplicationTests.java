package com.example.bookrental;

import com.example.bookrental.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookRentalMapperBackendApplicationTests {

    @Autowired
    BookService bookService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testBookInsert() throws Exception {

    }

    @Test
    @Transactional
    @Rollback(false)
    void contextLoads() {
        /*for (int i = 1; i <= 100; i++) {
            BookVO vo = new BookVO();
            vo.setIdNo("book_" + i);
            vo.setName("t_book_" + i);
            vo.setPrice(10000);
            vo.setRentalFee(500);
            vo.setRentalPeriod(3);
            vo.setMemo("this is test book data ..." + i);

            bookService.insertBook(vo);
        }*/

        /*BookVO vo = new BookVO();
        vo.setIdNo("book_" + 1);
        vo.setName("t_book_" + 1);
        vo.setPrice(10000);
        vo.setRentalFee(500);
        vo.setRentalPeriod(3);
        vo.setMemo("this is test book data ..." + 1);

        bookService.insertBook(vo);*/

        System.out.println("test!!!!! start???");
    }

}
