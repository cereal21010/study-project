package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.exception.ApiException;
import com.example.bookrentalbackend.exception.ExceptionEnum;
import com.example.bookrentalbackend.service.BookService;
import com.example.bookrentalbackend.service.CustomerService;
import com.example.bookrentalbackend.service.PointService;
import com.example.bookrentalbackend.service.RentalService;
import com.example.bookrentalbackend.vo.BookFileVO;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.search.BookSearchVO;
import com.example.bookrentalbackend.vo.BookVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/book")
public class BookApi {

    /**
     * update 파일추가, 삭제 기능 추가
     */

    private final BookService bookService;
    private final PointService pointService;
    private final RentalService rentalService;
    private final CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public ResponseEntity getBookList(BookSearchVO bookSearchVO) {

        System.out.println("== getBookList ==");

        Map<String, Object> responseMap = new HashMap<>();

        List<BookVO> bookList = bookService.getBookList(bookSearchVO);
        int totalRows = bookService.getBookCount(bookSearchVO);

        responseMap.put("bookList", bookList);
        responseMap.put("totalRows", totalRows);
        responseMap.put("searchParams", bookSearchVO);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{seq}", method = RequestMethod.GET)
    public ResponseEntity getBoardDetail(@PathVariable(value = "seq") long bookSeq, HttpServletRequest request) throws IOException {

        System.out.println("== getBoardDetail ==");

        String customerId = request.getHeader("Customer-Id");
        if (customerId == null || "null".equals(customerId) || "".equals(customerId)) {
            throw new ApiException(ExceptionEnum.SECURITY_04);
        }

        Map<String, Object> responseMap = new HashMap<>();

        CustomerVO customerVO = customerService.findCustomerById(customerId);
        BookVO bookVO = bookService.getBookDetail(bookSeq);
        List<BookFileVO> bookFileVOS = bookService.getBookFileList(bookSeq);
        int customerRemainPoint = pointService.getRemainPoint(customerId);
        boolean isLate = rentalService.findLateCustomer(customerId);

        responseMap.put("bookDetail", bookVO);
        responseMap.put("bookFileList", bookFileVOS);
        responseMap.put("remainPoint", customerRemainPoint);
        responseMap.put("isLate", isLate);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/image/{seq}", method = RequestMethod.GET)
    public ResponseEntity getImage(@PathVariable(value = "seq") long bookFileSeq) throws IOException {

        log.info("== getImage ==");

        Map<String, Object> imageMap = bookService.getImageByte(bookFileSeq);

        byte[] imageByteArray = (byte[]) imageMap.get("image");
        HttpHeaders headers = (HttpHeaders) imageMap.get("headers");

        return new ResponseEntity(imageByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity insertBook(@RequestPart(value = "requestBody") BookVO bookVO
            , @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        System.out.println("== insertBook ==");

        bookService.insertBook(bookVO, files);

        return new ResponseEntity(bookVO.getSeq(), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity updateBook(@RequestBody BookVO bookVO
            , @PathVariable("seq") long bookSeq) throws IOException {

        System.out.println("== updateBook ==");

        bookVO.setSeq(bookSeq);
        bookService.updateBook(bookVO);

        return new ResponseEntity(bookSeq, HttpStatus.OK);
    }

    @RequestMapping(value = "/file/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity updateBookFile(@RequestParam(value = "files", required = false) List<MultipartFile> files
            , @RequestParam(value = "deleteFileSeqs", required = false) List<Long> deleteFileSeqs
            , @PathVariable("seq") long bookSeq) throws IOException {

        System.out.println("== updateBookFile ==");

        bookService.updateBookFile(bookSeq, files, deleteFileSeqs);

        return new ResponseEntity(bookSeq, HttpStatus.OK);
    }


    @RequestMapping(value = "/delete/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable(value = "seq") long bookSeq) {

        System.out.println("== deleteBook ==");

        bookService.deleteBook(bookSeq);

        return new ResponseEntity(bookSeq, HttpStatus.OK);
    }


    /**
     * 이미지 출력 테스트
     */
    /*@RequestMapping(value = "/display")
    public ResponseEntity getImage(String FileName) {

    }*/





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

