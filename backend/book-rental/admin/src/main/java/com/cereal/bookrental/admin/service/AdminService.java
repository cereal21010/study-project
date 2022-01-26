package com.cereal.bookrental.admin.service;

import com.cereal.bookrental.admin.vo.AdminVO;
import com.cereal.bookrental.admin.exception.ApiException;
import com.cereal.bookrental.admin.exception.ExceptionEnum;
import com.cereal.bookrental.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(adminVO.getPassword());
        adminVO.setPassword(encodedPassword);
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
            if ( passwordEncoder.matches(password, adminVO.getPassword()) ) {
                return adminVO;
            } else {
                throw new ApiException(ExceptionEnum.PASSWORD_MISMATCH);
            }
        } else {
            throw new ApiException(ExceptionEnum.NOT_FIND_EMAIL);
        }

    }
}
