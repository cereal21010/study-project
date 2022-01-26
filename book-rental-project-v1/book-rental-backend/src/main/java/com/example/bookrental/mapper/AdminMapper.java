package com.example.bookrental.mapper;

import com.example.bookrental.vo.AdminVO;

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
