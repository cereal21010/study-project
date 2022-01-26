package com.example.bookrental.mapper;

import com.example.bookrental.vo.PointVO;
import com.example.bookrental.vo.search.PointSearchVO;

import java.util.List;

public interface PointMapper {

    public List<PointVO> getPointList(PointSearchVO pointSearchVO);
    public void insertPoint(PointVO pointVO);
    public List<PointVO> findPointListByCustomer(long customerSeq);

    public PointVO findPointByCustomer(long customerSeq);

}
