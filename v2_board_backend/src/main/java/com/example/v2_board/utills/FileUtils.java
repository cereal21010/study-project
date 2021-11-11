package com.example.v2_board.utills;

import com.example.v2_board.vo.BoardVO;
import com.example.v2_board.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Component
@Slf4j
public class FileUtils {
    public List<FileVO> parseFileInfo(BoardVO vo, List<MultipartFile> files) throws Exception {

        int boardSeq = vo.getSeq();

        List<FileVO> fileList = new ArrayList<>();

        File target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board_backend\\file_dir");
        if (!target.exists()) target.mkdirs();

        for (MultipartFile file : files) {
            String orgFileName = file.getOriginalFilename();
            String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));
            String saveFileName = UUID.randomUUID().toString().replace("-", "") + orgFileExtension;
            Long saveFileSize = file.getSize();

            log.info("================== file start ==================");
            log.info("파일 실제 이름: " + orgFileName);
            log.info("파일 저장 이름: " + saveFileName);
            log.info("파일 크기: " + saveFileSize);
            log.info("content type: " + file.getContentType());
            log.info("================== file   END ==================");

            target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board_backend\\file_dir", saveFileName);
            file.transferTo(target);

            FileVO fileVO = new FileVO();

            fileVO.setOriginalName(orgFileName);
            fileVO.setSaveName(saveFileName);
            fileVO.setSaveSize(saveFileSize);
            fileVO.setBoardSeq(boardSeq);
            fileList.add(fileVO);
        }
        return fileList;

    }
}
