package com.cereal.bookrental.user.service;

import com.cereal.bookrental.user.exception.ApiException;
import com.cereal.bookrental.user.exception.ExceptionEnum;
import com.cereal.bookrental.user.mapper.CustomerMapper;
import com.cereal.bookrental.user.vo.CustomerVO;
import com.cereal.bookrental.user.vo.search.CustomerSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(customerVO.getPassword());
        customerVO.setPassword(encodedPassword);
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
        CustomerVO customer = customerMapper.findCustomerById(id);

        if (customer != null) {
            if ( passwordEncoder.matches(password, customer.getPassword()) ) {
                return customer;
            } else {
                throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH);
            }
        } else {
            throw new ApiException(ExceptionEnum.NOT_FIND_EMAIL);
        }
    }

}
