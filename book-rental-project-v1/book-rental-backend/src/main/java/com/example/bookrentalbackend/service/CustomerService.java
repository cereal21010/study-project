package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.exception.ApiException;
import com.example.bookrentalbackend.exception.ExceptionEnum;
import com.example.bookrentalbackend.mapper.CustomerMapper;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.search.CustomerSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    public List<CustomerVO> getCustomerList(CustomerSearchVO customerSearchVO) {
        return customerMapper.getCustomerList(customerSearchVO);
    }

    public int getCustomerCount(CustomerSearchVO customerSearchVO) {
        return customerMapper.getCustomerCount(customerSearchVO);
    }

    public CustomerVO getCustomerDetail(long customerSeq) {
        return customerMapper.findCustomerBySeq(customerSeq);
    }

    public CustomerVO findCustomerById(String customerId){
        return customerMapper.findCustomerById(customerId);
    }


    public void insertCustomer(CustomerVO customerVO) {
        customerMapper.insertCustomer(customerVO);
    }

    public CustomerVO updateCustomer(CustomerVO customerVO) {
        long customerSeq = customerMapper.updateCustomer(customerVO);

        return customerMapper.findCustomerBySeq(customerSeq);
    }

    public void deleteCustomer(long customerSeq) {
        customerMapper.deleteCustomer(customerSeq);
    }

    public CustomerVO loginCustomer(String id, String password) {
        CustomerVO customerVO = customerMapper.findCustomerById(id);

        if (customerVO != null) {
            if ( password.equals(customerVO.getPassword()) ) {
                return customerVO;
            } else {
                throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH);
            }
        } else {
            throw new ApiException(ExceptionEnum.NOT_FIND_EMAIL);
        }
    }

}
