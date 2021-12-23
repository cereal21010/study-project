package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.service.BookService;
import com.example.bookrentalbackend.vo.search.BookSearchVO;
import com.example.bookrentalbackend.vo.BookVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/book")
public class BookApi {
    
    /*
    * update 파일추가, 삭제 기능 추가
    * */

    private final BookService bookService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getBookList(BookSearchVO searchVO) {

        System.out.println("== getBookList ==");

        List<BookVO> books = bookService.getBookList(searchVO);

        return new ResponseEntity(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{seq}", method = RequestMethod.GET)
    public ResponseEntity getBoardDetail(@PathVariable(value = "seq") long bookSeq) {

        System.out.println("== getBoardDetail ==");
        BookVO bookVO = bookService.getBookDetail(bookSeq);

        return new ResponseEntity(bookVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity insertBook(@RequestPart(value = "responseBody") BookVO bookVO
                                , @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        System.out.println("== insertBook ==");

        bookService.insertBook(bookVO, files);

        return new ResponseEntity(bookVO.getSeq(), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity updateBook(@RequestPart(value = "responseBody") BookVO bookVO
                                , @RequestPart(value = "files", required = false) List<MultipartFile> files
                                , @RequestPart(value = "deleteFileSeqs", required = false) List<Long> deleteFileSeqs
                                , @PathVariable("seq") long bookSeq) throws IOException {

        System.out.println("== updateBook ==");

        bookVO.setSeq(bookSeq);
        bookService.updateBook(bookVO, files, deleteFileSeqs);

        return new ResponseEntity(bookSeq, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable(value = "seq") long bookSeq) {

        System.out.println("== deleteBook ==");

        bookService.deleteBook(bookSeq);

        return new ResponseEntity(bookSeq, HttpStatus.OK);
    }





    /*@RequestMapping(value = "/test/data/insert", method = RequestMethod.GET)
    public ResponseEntity testDataInsert() {

        for (int i = 1; i <= 100; i++) {
            BookVO vo = new BookVO();
            vo.setIdNo("book_" + i);
            vo.setName("t_book_" + i);
            vo.setPrice(10000);
            vo.setRentalFee(500);
            vo.setRentalPeriod(3);
            vo.setMemo("this is test book data ..." + i);

            bookService.insertBook(vo);
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }*/


}

