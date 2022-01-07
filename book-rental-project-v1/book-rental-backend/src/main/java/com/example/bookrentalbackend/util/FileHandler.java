package com.example.bookrentalbackend.util;

import com.example.bookrentalbackend.vo.BookFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    final String filePath = "C:\\Users\\tlduf\\workspace\\study-project\\book-rental-project-v1\\book-rental-backend\\file-dir";

    public List<BookFileVO> parseFileInfo(long bookSeq, List<MultipartFile> files) throws IOException {

        List<BookFileVO> fileList = new ArrayList<>();

        File target = new File(filePath);
        if (!target.exists()) {
            target.mkdirs();
        }

        for (MultipartFile file : files) {
            String orgFileName = file.getOriginalFilename();
            String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));  //확장자
            String saveFileName = UUID.randomUUID().toString().replace("-", "") + orgFileExtension;
            long saveFileSize = file.getSize();

            log.info("========= file start =========");
            System.out.println("파일 실제 이름: " + orgFileName);
            System.out.println("파일 저장 이름: " + saveFileName);
            System.out.println("파일 크기: " + saveFileSize);
            System.out.println("content type: " + file.getContentType());
            System.out.println("========= file end =========");

            target = new File(filePath, saveFileName);
            file.transferTo(target);

            BookFileVO bookFileVO = new BookFileVO();

            bookFileVO.setOriginalName(orgFileName);
            bookFileVO.setSaveName(saveFileName);
            bookFileVO.setSaveSize(saveFileSize);
            bookFileVO.setFilePath(filePath);
            bookFileVO.setBookSeq(bookSeq);
            fileList.add(bookFileVO);
        }
        return fileList;
    }

    public void deleteBookFile(List<BookFileVO> bookFiles) {
        for (BookFileVO bookFileVO : bookFiles) {
            File file = new File(filePath, bookFileVO.getSaveName());
            if (file.isFile()) {
                if(file.delete()) {
                    System.out.println("success file delete");
                } else {
                    System.out.println("fail file delete");
                }
            } else {
                System.out.println("not find file.");
            }
        }
    }

}
