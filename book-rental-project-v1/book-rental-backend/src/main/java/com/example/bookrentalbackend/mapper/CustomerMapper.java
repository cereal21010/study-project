package com.example.bookrentalbackend.mapper;

import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.search.CustomerSearchVO;

import java.util.List;

public interface CustomerMapper {

    public List<CustomerVO> getCustomerList(CustomerSearchVO customerSearchVO);
    public int getCustomerCount(CustomerSearchVO customerSearchVO);
    public CustomerVO findCustomerBySeq(long seq);
    public CustomerVO findCustomerById(String id);
    public void insertCustomer(CustomerVO customerVO);
    public long updateCustomer(CustomerVO customerVO);
    public long deleteCustomer(long seq);

}
