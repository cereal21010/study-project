package com.cereal.bookrental.admin.mapper;

import com.cereal.bookrental.admin.vo.AdminVO;

import java.util.List;
import java.util.Map;

public interface AdminMapper {

    public List<AdminVO> getAdminList(Map paramMap);
    public AdminVO findAdminBySeq(long seq);
    public AdminVO findAdminById(String id);
    public void insertAdmin(AdminVO adminVO);
    public long updateAdmin(AdminVO adminVO);
    public long deleteAdmin(long seq);
}
