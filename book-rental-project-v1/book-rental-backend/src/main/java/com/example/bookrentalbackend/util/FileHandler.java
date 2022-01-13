package com.example.bookrentalbackend.util;

import com.example.bookrentalbackend.vo.BookFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    final String FILE_PATH = "C:\\Users\\tlduf\\workspace\\study-project\\book-rental-project-v1\\book-rental-backend\\file-dir";

    public List<BookFileVO> parseFileInfo(long bookSeq, List<MultipartFile> files) throws IOException {

        List<BookFileVO> fileList = new ArrayList<>();

        File target = new File(FILE_PATH);
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

            /**
             * 파일 저장
             */
            target = new File(FILE_PATH, saveFileName);
            Image image = ImageIO.read(file.getInputStream());
            image = image.getScaledInstance(300, 350, Image.SCALE_SMOOTH);
            BufferedImage newImage = new BufferedImage(300, 350, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(newImage, orgFileExtension.substring(1), target);
            

            BookFileVO bookFileVO = new BookFileVO();

            bookFileVO.setOriginalName(orgFileName);
            bookFileVO.setSaveName(saveFileName);
            bookFileVO.setSaveSize(saveFileSize);
            bookFileVO.setFilePath(FILE_PATH);
            bookFileVO.setBookSeq(bookSeq);
            fileList.add(bookFileVO);
        }
        return fileList;
    }

    public void deleteBookFile(List<BookFileVO> bookFiles) {
        for (BookFileVO bookFileVO : bookFiles) {
            File file = new File(FILE_PATH, bookFileVO.getSaveName());
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

    public byte[] imagePrint(BookFileVO bookFile) throws IOException {

        InputStream imageStream = new FileInputStream(FILE_PATH + "\\" + bookFile.getSaveName());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

    public HttpHeaders getHeaders(BookFileVO bookFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Path filePath = Paths.get(FILE_PATH + "\\" + bookFile.getSaveName());
        headers.add("Content-Type", Files.probeContentType(filePath));
        return headers;
    }

}
