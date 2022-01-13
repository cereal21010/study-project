package com.example.bookrentalbackend.api;

import com.example.bookrentalbackend.exception.ApiException;
import com.example.bookrentalbackend.exception.ExceptionEnum;
import com.example.bookrentalbackend.service.CustomerService;
import com.example.bookrentalbackend.service.RentalService;
import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.RentalVO;
import com.example.bookrentalbackend.vo.response.ResponseRentalVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/rental")
public class RentalApi {

    private final RentalService rentalService;
    private final CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getRentalList(RentalSearchVO rentalSearchVO, HttpServletRequest request) {

        Map<String, Object> responseMap = new HashMap<>();
//        List<RentalVO> rentalVOS = rentalService.getRentalList(rentalSearchVO);

        String customerId = request.getHeader("Customer-Id");
        if (customerId == null || "null".equals(customerId) || "".equals(customerId)) {
            throw new ApiException(ExceptionEnum.SECURITY_04);
        }
        CustomerVO customerVO = customerService.findCustomerById(customerId);
        List<ResponseRentalVO> rentalVOS = rentalService.getResponseRental(customerVO.getSeq());

        responseMap.put("rentalList", rentalVOS);
        responseMap.put("searchParams", rentalSearchVO);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity rentalBook(@RequestBody BookVO bookVO) {

        String testCustomer = "tid01";

        rentalService.rentalBook(bookVO, testCustomer);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity returnBook(@PathVariable(value = "seq") long rentalSeq) {

        rentalService.updateReturn(rentalSeq);

        return new ResponseEntity(HttpStatus.OK);
    }
}
