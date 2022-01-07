package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.mapper.AdminMapper;
import com.example.bookrentalbackend.vo.AdminVO;
import com.example.bookrentalbackend.vo.CustomerVO;
import com.example.bookrentalbackend.vo.search.CustomerSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

    public List<AdminVO> getAdminList(String id) {
        if (id == null || "".equals(id)) {
            return adminMapper.getAdminList(Map.of("id", ""));
        } else {
            return adminMapper.getAdminList(Map.of("id", id));
        }

    }

    public AdminVO getAdminDetail(long adminSeq) {
        return adminMapper.findAdminBySeq(adminSeq);
    }

    public void insertAdmin(AdminVO adminVO) {
        adminMapper.insertAdmin(adminVO);
    }

    public AdminVO updateAdmin(AdminVO adminVO) {
        long adminSeq = adminMapper.updateAdmin(adminVO);

        return adminMapper.findAdminBySeq(adminSeq);
    }

    public void deleteAdmin(long adminSeq) {
        adminMapper.deleteAdmin(adminSeq);
    }
}
