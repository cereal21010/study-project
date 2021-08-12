package com.example.v2_board.api;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@Slf4j
public class BoardApi {

    private final BoardService boardService;
    private final FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<?> saveBoard(@RequestParam("files") List<MultipartFile> files, BoardDTO dto) throws Exception{
        log.info("-- api board save --");

        dto.setWriterSeq(1);
        dto.setWriter("user01");
        boardService.insert(dto);

        if( !files.get(0).isEmpty() ){
            fileService.saveFile(files, dto);
//            files.stream().map(file -> uploadFile(file)).collect(Collectors.toList());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("-- api board upload file --");
        if( file.isEmpty() ){ return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        String fileName = fileService.saveFile(file);
        String downloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
        log.info(downloadURI);
        return new ResponseEntity<>(downloadURI, HttpStatus.OK);
    }*/

    /*@GetMapping("/download")
    public ResponseEntity<?> downloadFile(String fileName, HttpServletRequest request) {
        Resource resource = null;
        String contentType = null;
        try {
            resource = fileService.loadFile(fileName);
            contentType = request.getServletContext().getMimeType( resource.getFile().getAbsolutePath() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.info("Could not determine file type.");
        }

        if( contentType == null ){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType( MediaType.parseMediaType(contentType) )
                .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"" )
                .body(resource);

    }*/

    @PostMapping("/delete")
    public Map<String, Object> deleteBoard(BoardDTO dto) throws Exception{
        log.info("-- api board delete --");
        Map<String, Object> map = new HashMap<>();
        boardService.delete(dto.getSeq());
        map.put("result", "000");
        return map;
    }

    @PostMapping("/update")
    public Map<String, Object> updateBoard(BoardDTO dto) throws Exception{
        log.info("-- api board update --");
        Map<String, Object> map = new HashMap<>();
        boardService.update(dto);
        map.put("result", "000");
        return map;
    }

}
