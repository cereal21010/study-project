package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.service.CustomerService;
import com.example.bookrentalbackend.service.RentalService;
import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.RentalVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rental")
public class RentalApi {

    private final RentalService rentalService;
    private final CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getRentalList(RentalSearchVO rentalSearchVO) {

        List<RentalVO> rentalVOS = rentalService.getRentalList(rentalSearchVO);

        return new ResponseEntity(rentalVOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity rentalBook(@RequestBody BookVO bookVO) {

        String testCustomer = "tid01";
        CustomerVO customerVO = customerService.findCustomerById(testCustomer);

        rentalService.insertRental(bookVO, customerVO.getSeq());

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity returnBook(@PathVariable(value = "seq") long rentalSeq) {

        rentalService.updateRental(rentalSeq);

        return new ResponseEntity(HttpStatus.OK);
    }
}
