package com.example.v2_board.utills;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Component
@Slf4j
public class FileUtils {
    public List<FileDTO> parseFileInfo(BoardDTO dto, List<MultipartFile> files) throws Exception{

        int boardSeq = dto.getSeq();

        List<FileDTO> fileList =  new ArrayList<>();

        File target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board\\file_dir");
        if(!target.exists()) target.mkdirs();

        for(MultipartFile file : files) {
            String orgFileName = file.getOriginalFilename();
            String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));
            String saveFileName = UUID.randomUUID().toString().replace("-", "")+orgFileExtension;
            Long saveFileSize = file.getSize();

            log.info("================== file start ==================");
            log.info("파일 실제 이름: "+orgFileName);
            log.info("파일 저장 이름: "+saveFileName);
            log.info("파일 크기: "+saveFileSize);
            log.info("content type: "+file.getContentType());
            log.info("================== file   END ==================");

            target = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board\\file_dir", saveFileName);
            file.transferTo(target);

            FileDTO fileDTO = new FileDTO();

            fileDTO.setOriginalName(orgFileName);
            fileDTO.setSaveName(saveFileName);
            fileDTO.setSaveSize(saveFileSize);
            fileDTO.setBoardSeq(boardSeq);
            fileList.add(fileDTO);
        }
        return fileList;

    }
}
