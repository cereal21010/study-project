package com.example.bookrental.service;

import com.example.bookrental.exception.ApiException;
import com.example.bookrental.exception.ExceptionEnum;
import com.example.bookrental.mapper.AdminMapper;
import com.example.bookrental.vo.AdminVO;
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

    public AdminVO loginAdmin(String id, String password) {
        AdminVO adminVO = adminMapper.findAdminById(id);

        if (adminVO != null) {
            if ( password.equals(adminVO.getPassword()) ) {
                return adminVO;
            } else {
                throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH);
            }
        } else {
            throw new ApiException(ExceptionEnum.NOT_FIND_EMAIL);
        }

    }
}
