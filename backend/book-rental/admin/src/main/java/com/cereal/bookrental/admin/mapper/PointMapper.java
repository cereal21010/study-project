package com.cereal.bookrental.admin.mapper;

import com.cereal.bookrental.admin.vo.PointVO;
import com.cereal.bookrental.admin.vo.search.PointSearchVO;

import java.util.List;

public interface PointMapper {

    public List<PointVO> getPointList(PointSearchVO pointSearchVO);
    public void insertPoint(PointVO pointVO);
    public List<PointVO> findPointListByCustomer(long customerSeq);

    public PointVO findPointByCustomer(long customerSeq);

}
