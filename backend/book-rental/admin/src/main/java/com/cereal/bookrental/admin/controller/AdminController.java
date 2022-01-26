package com.cereal.bookrental.admin.controller;

import com.cereal.bookrental.admin.service.AdminService;
import com.cereal.bookrental.admin.service.RentalService;
import com.cereal.bookrental.admin.service.login.JwtService;
import com.cereal.bookrental.admin.vo.AdminVO;
import com.cereal.bookrental.admin.vo.LoginVO;
import com.cereal.bookrental.admin.vo.response.ResponseRentalVO;
import com.cereal.bookrental.admin.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final RentalService rentalService;
    private final JwtService jwtService;

    @RequestMapping(value = "/api/admin/list", method = RequestMethod.GET)
    public ResponseEntity getAdminList(AdminVO adminVO) {

        List<AdminVO> adminVOS = adminService.getAdminList(adminVO.getId());

        return new ResponseEntity(adminVOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/detail/{seq}", method = RequestMethod.GET)
    public ResponseEntity getAdminDetail(@PathVariable(value = "seq") long adminSeq) {

        AdminVO adminVO = adminService.getAdminDetail(adminSeq);

        return new ResponseEntity(adminVO, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/admin/insert", method = RequestMethod.POST)
    public ResponseEntity insertAdmin(@RequestBody AdminVO adminVO) {

        adminService.insertAdmin(adminVO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/update/{seq}", method = RequestMethod.PUT)
    public ResponseEntity updateAdmin(@RequestBody AdminVO adminVO
            , @PathVariable(value = "seq") long adminSeq) {

        adminVO.setSeq(adminSeq);
        AdminVO updateAdmin = adminService.updateAdmin(adminVO);

        return new ResponseEntity(updateAdmin, HttpStatus.OK);

    }

    @RequestMapping(value = "/api/admin/delete/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAdmin(@PathVariable(value = "seq") long adminSeq) {

        adminService.deleteAdmin(adminSeq);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/customer/rental/list/{seq}", method = RequestMethod.GET)
    public ResponseEntity rentalList(@PathVariable(value = "seq") long customerSeq) {

        Map<String, Object> responseMap = new HashMap<>();

        List<ResponseRentalVO> rentalVOS = rentalService.getResponseRental(customerSeq);
        responseMap.put("rentalList", rentalVOS);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/customer/rental/return/{seq}", method = RequestMethod.GET)
    public ResponseEntity bookReturn(@PathVariable(value = "seq") long rentalSeq) {

        rentalService.updateReturn(rentalSeq);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/login", method = RequestMethod.POST)
    public ResponseEntity loginAdmin(@RequestBody LoginVO loginVO, HttpServletResponse response) {
        Map<String, Object> responseMap = new HashMap<>();
        HttpStatus status = null;

        try {
            AdminVO adminVO = adminService.loginAdmin(loginVO.getId(), loginVO.getPassword());
            String token = jwtService.createAdmin(adminVO);
            response.setHeader("Authorization", token);
            responseMap.put("status", true);
            responseMap.put("loginInfo", adminVO);
            responseMap.put("role", "admin");
            status = HttpStatus.ACCEPTED;
        } catch (ApiException e) {
            throw new ApiException(e.getError());
        }
        return new ResponseEntity(responseMap, status);
    }

}
