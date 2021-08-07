package com.example.v2_board.utills;

import com.example.v2_board.dto.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Component
@Slf4j
public class FileUtils {
    public List<Map<String, Object>> parseFileInfo(BoardDTO dto, MultipartFile[] file) throws Exception{

        long boardSeq = dto.getSeq();

        List<Map<String, Object>> fileList =  new ArrayList<>();

        File target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board\\file_dir");
        if(!target.exists()) target.mkdirs();

        for(int i=0; i < file.length; i++) {
            String orgFileName = file[i].getOriginalFilename();
            String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));
            String saveFileName = UUID.randomUUID().toString().replace("-", "")+orgFileExtension;
            Long saveFileSize = file[i].getSize();

            log.info("================== file start ==================");
            log.info("파일 실제 이름: "+orgFileName);
            log.info("파일 저장 이름: "+saveFileName);
            log.info("파일 크기: "+saveFileSize);
            log.info("content type: "+file[i].getContentType());
            log.info("================== file   END ==================");

            target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board\\file_dir", saveFileName);
            file[i].transferTo(target);

            Map<String, Object> fileInfo = new HashMap<>();

            fileInfo.put("BOARD_SEQ", boardSeq);
            fileInfo.put("ORG_FILE_NAME", orgFileName);
            fileInfo.put("SAVE_FILE_NAME", saveFileName);
            fileInfo.put("FILE_SIZE", saveFileSize);
            fileList.add(fileInfo);
        }
        return fileList;

    }
}
