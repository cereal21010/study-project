package com.example.v2_board_backend_t1.util;

import com.example.v2_board_backend_t1.vo.BoardFileVO;
import com.example.v2_board_backend_t1.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileHandler {

    public List<BoardFileVO> parseFileInfo(int boardSeq, List<MultipartFile> files) throws Exception {

        List<BoardFileVO> fileList = new ArrayList<>();

        File target = new File("C:\\Users\\tlduf\\workspace\\study-project\\practice-project\\v2_board_backend_t1\\file_dir");
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

            target = new File("C:\\Users\\tlduf\\workspace\\study-project\\practice-project\\v2_board_backend_t1\\file_dir", saveFileName);
            file.transferTo(target);

            BoardFileVO boardFileVO = new BoardFileVO();

            boardFileVO.setOriginalName(orgFileName);
            boardFileVO.setSaveName(saveFileName);
            boardFileVO.setSaveSize(saveFileSize);
            boardFileVO.setBoardSeq(boardSeq);
            fileList.add(boardFileVO);
        }
        return fileList;

    }

    public void deleteBoardFile(List<BoardFileVO> boardFiles) {
        for (BoardFileVO boardFileVO : boardFiles) {
            File file = new File("C:\\Users\\tlduf\\workspace\\study-project\\practice-project\\v2_board_backend_t1\\file_dir", boardFileVO.getSaveName());
            if (file.isFile()) {
                if (file.delete()) {
                    log.info("success file delete");
                } else {
                    log.error("Cannot delete file.");
                }
            }
        }
    }


}
