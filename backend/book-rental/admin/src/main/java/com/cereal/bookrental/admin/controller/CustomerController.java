package com.cereal.bookrental.admin.controller;

import com.cereal.bookrental.admin.service.CustomerService;
import com.cereal.bookrental.admin.service.login.JwtService;
import com.cereal.bookrental.admin.vo.CustomerVO;
import com.cereal.bookrental.admin.vo.LoginVO;
import com.cereal.bookrental.admin.vo.search.CustomerSearchVO;
import com.cereal.bookrental.admin.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getCustomerList(CustomerSearchVO customerSearchVO) {

        log.info("== getCustomerList ==");

        Map<String, Object> responseMap = new HashMap<>();

        List<CustomerVO> customerVOS = customerService.getCustomerList(customerSearchVO);
        int totalRows = customerService.getCustomerCount(customerSearchVO);

        responseMap.put("customerList", customerVOS);
        responseMap.put("totalRows", totalRows);
        responseMap.put("searchParams", customerSearchVO);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{seq}", method = RequestMethod.GET)
    public ResponseEntity getCustomerDetail(@PathVariable(value = "seq") long customerSeq) {

        CustomerVO customerVO = customerService.getCustomerDetail(customerSeq);

        return new ResponseEntity(customerVO, HttpStatus.OK);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity insertCustomer(@RequestBody CustomerVO customerVO) {

        log.info("== insertCustomer ==");

        customerService.insertCustomer(customerVO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity updateCustomer(@RequestBody CustomerVO customerVO
            , @PathVariable(value = "seq") long customerSeq) {

        customerVO.setSeq(customerSeq);
        CustomerVO updateCustomer = customerService.updateCustomer(customerVO);

        return new ResponseEntity(updateCustomer, HttpStatus.OK);

    }

    /**
     *
     * @param customersSeq
     * @return
     */
    @RequestMapping(value = "/delete/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCustomer(@PathVariable(value = "seq") long customersSeq) {

        customerService.deleteCustomer(customersSeq);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginCustomer(@RequestBody LoginVO loginVO, HttpServletResponse response) {
        Map<String, Object> responseMap = new HashMap<>();
        HttpStatus status = null;

        try {
            CustomerVO customerVO = customerService.loginCustomer(loginVO.getId(), loginVO.getPassword());
            String token = jwtService.create(customerVO);
            response.setHeader("Authorization", token);
            responseMap.put("status", true);
            responseMap.put("loginInfo", customerVO);
            responseMap.put("role", "customer");
            status = HttpStatus.ACCEPTED;
        } catch (ApiException e) {
            throw new ApiException(e.getError());
        }
        return new ResponseEntity(responseMap, status);
    }

    /*@RequestMapping(value = "/test/data/insert", method = RequestMethod.GET)
    public ResponseEntity testDataInsert() {

        for (int i = 1; i <= 100; i++) {
            CustomerVO vo = new CustomerVO();
            vo.setId("tcust" + i);
            vo.setPassword("123");
            vo.setName("tcuster" + i);
            int birthday = Integer.parseInt( "200" + (i % 10) + "0305" );
            vo.setBirthday(birthday);
            vo.setPhoneNumber(01012341234);
            vo.setMemo("this is test customer data ..." + i);

            customerService.insertCustomer(vo);
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }*/

}
