package com.example.v2_board.api;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.FileDTO;
import com.example.v2_board.dto.SearchDTO;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.service.FileService;
import com.example.v2_board.utills.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
public class BoardApi {

    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping("/list")
    public List<BoardDTO> boardList(Model model, SearchDTO searchDTO) throws Exception {
        //검색을 했을 때는 다시 1페이지로 ( 현재는 3페이지에서 검색했을 경우 3페이지 그대로 있다 )
        log.info("-- api board list --");

        PageMaker pm = boardService.getPageMaker(searchDTO);
        List<BoardDTO> boardList = boardService.selectList(searchDTO);
        return boardList;
    }

    /*@PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestParam("files") List<MultipartFile> files, BoardDTO dto) throws Exception{
        log.info("-- api board save --");

        dto.setWriterSeq(1);
        dto.setWriter("user01");
        boardService.insert(dto);

        if( !files.get(0).isEmpty() ){
            fileService.saveFile(files, dto);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(BoardDTO boardDetail) throws Exception{
        log.info("-- api board save --");

        boardDetail.setWriterSeq(1);
        boardDetail.setWriter("user01");
        boardService.insert(boardDetail);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/downloadFile")
    public ResponseEntity<?> downloadFile(HttpServletResponse response, @RequestParam("seq")int seq) {
        try {
            FileDTO dto = fileService.getOne(seq);
            //dto가 null일 경우 에러 처리
            String fileName = new String( dto.getSaveName().toString().getBytes("euc-kr"), "iso-8859-1" );
            String orgFileName = new String( dto.getOriginalName().toString().getBytes("euc-kr"), "iso-8859-1" );
            File file = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board\\file_dir\\"+dto.getSaveName());
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if( mimeType == null ) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename=\""+orgFileName+"\"");
            response.setContentLength( (int)file.length() );
            InputStream inputStream = new BufferedInputStream( new FileInputStream(file) );
            FileCopyUtils.copy( inputStream, response.getOutputStream() );

        } catch (Exception e) {
            e.printStackTrace();
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
    public Map<String, Object> updateBoard(@RequestParam("deleteFileList")List<Integer> deleteFileList
                                         , @RequestParam("files") List<MultipartFile> files
                                         , BoardDTO dto) throws Exception{
        log.info("-- api board update --");
        Map<String, Object> map = new HashMap<>();
        boardService.update(dto);
        if( !files.get(0).isEmpty() ){
            fileService.saveFile(files, dto);
        }
        for(Integer seq : deleteFileList){
            FileDTO fileDTO = fileService.getOne(seq);
            if( fileDTO != null ) {
                fileService.deleteOne(fileDTO);
            }
        }
        map.put("result", "000");
        return map;
    }

}
