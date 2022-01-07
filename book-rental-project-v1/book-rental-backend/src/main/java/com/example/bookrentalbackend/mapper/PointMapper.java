package com.example.bookrentalbackend.mapper;

import com.example.bookrentalbackend.vo.PointVO;
import com.example.bookrentalbackend.vo.search.PointSearchVO;

import java.util.List;

public interface PointMapper {

    public List<PointVO> getPointList(PointSearchVO pointSearchVO);
    public void insertPoint(PointVO pointVO);
    public PointVO findPointByIdLastOrder(long customerSeq);

}
