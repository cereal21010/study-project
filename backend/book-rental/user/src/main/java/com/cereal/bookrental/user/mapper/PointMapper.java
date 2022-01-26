package com.cereal.bookrental.user.mapper;

import com.cereal.bookrental.user.vo.PointVO;
import com.cereal.bookrental.user.vo.search.PointSearchVO;

import java.util.List;

public interface PointMapper {

    public List<PointVO> getPointList(PointSearchVO pointSearchVO);
    public void insertPoint(PointVO pointVO);
    public List<PointVO> findPointListByCustomer(long customerSeq);

    public PointVO findPointByCustomer(long customerSeq);

}
