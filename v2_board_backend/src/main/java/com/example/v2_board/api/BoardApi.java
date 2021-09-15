package com.example.v2_board.api;

import com.example.v2_board.service.CommentService;
import com.example.v2_board.service.RecommendService;
import com.example.v2_board.vo.*;
import com.example.v2_board.service.BoardService;
import com.example.v2_board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@Slf4j
public class BoardApi {

    private final BoardService boardService;
    private final FileService fileService;
    private final CommentService commentService;
    private final RecommendService recommendService;

    @RequestMapping(value = "/moreList", method = {RequestMethod.GET})
    public ResponseEntity<?> moreGetList(SearchVO searchVO) {
        log.info("-- api more list --");

        List<BoardVO> boardList = boardService.moreGetList(searchVO);
        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("search", searchVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ResponseEntity<?> boardList(Model model, SearchVO searchVO) throws Exception {
        log.info("-- api board list --");

//        PageMaker pagination = boardService.getPageMaker(searchVO);
        List<BoardVO> boardList = boardService.selectList(searchVO);
        searchVO.setTotalCount( boardService.totalCount(searchVO) );
        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("search", searchVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{seq}", method = {RequestMethod.GET})
    public ResponseEntity<?> boardDetail(@PathVariable("seq") int seq) throws Exception {
        log.info("-- board detail --");

        int testMemberSeq = 2;

        boardService.increaseViewCount(seq);    //조회수 증가

        BoardVO boardDetail =  boardService.getOne(seq);
        List<FileVO> files = fileService.selectList(boardDetail.getSeq());
        List<CommentVO> comments = commentService.getCommentList(seq);
        int recommendCount = recommendService.getReCommendCount(seq);
        Boolean isRecommended = recommendService.isRecommended(seq, testMemberSeq); //테스트로 memberSeq: 1, 2
        
        Map<String, Object> response = new HashMap<>();
        response.put("boardDetail", boardDetail);
        response.put("files", files);
        response.put("comments", comments);
        response.put("recommendCount", recommendCount);
        response.put("isRecommended", isRecommended);
//        List<FileVO> files = fileService.selectList(board.getSeq());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    public ResponseEntity<?> saveBoard( @RequestPart("requestBody") BoardVO vo
                                    , HttpServletRequest request ) throws Exception{
        log.info("-- api board insert --");

        vo.setWriterSeq(1);
        vo.setWriter("user01");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("files");

        try {
            boardService.insert(vo);

            System.out.println(files.size());

            if( files != null && files.size() > 0) {
                if (!files.get(0).isEmpty()) {
                    fileService.saveFile(files, vo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping( value = "/delete/{seq}", method = {RequestMethod.DELETE} )
    public ResponseEntity<?> deleteBoard(@PathVariable("seq") int seq) throws Exception{
        log.info("-- api board delete --");
        Map<String, Object> map = new HashMap<>();
        boardService.delete(seq);
        map.put("result", "000");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT})
    public ResponseEntity<?> updateBoard(@RequestParam(value = "files", required = false) List<MultipartFile> files
                                         , @RequestParam(value = "deleteFileList", required = false) List<Integer> deleteFileList
                                         , @RequestPart("requestBody") BoardVO vo) throws Exception{
        log.info("-- api board update --");

        BoardVO boardVO = boardService.getOne(vo.getSeq());
        boardService.insertChangedBoard( boardVO );

        boardService.update(vo);
        if( files != null ) {
            if (!files.get(0).isEmpty()) {
                fileService.saveFile(files, vo);
            }
        }
        for(Integer seq : deleteFileList){
            FileVO fileVO = fileService.getOne(seq);
            if( fileVO != null ) {
                fileService.deleteOne(fileVO);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/downloadFile")
    public void downloadFile(HttpServletResponse response, @RequestParam("seq")int seq) {
        try {
            FileVO vo = fileService.getOne(seq);
            //vo가 null일 경우 에러 처리
            String fileName = new String( vo.getSaveName().toString().getBytes("utf-8"), "iso-8859-1" );
//            String orgFileName = new String( vo.getOriginalName().toString().getBytes("utf-8"), "iso-8859-1" );
            String orgFileName = URLEncoder.encode(vo.getOriginalName(), "utf-8");

            File file = new File("C:\\Users\\tlduf\\workspace\\study-project\\v2_board_backend\\file_dir\\"+vo.getSaveName());
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

//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/changes/{boardSeq}", method = RequestMethod.GET)
    public ResponseEntity getChangedBoardList(@PathVariable("boardSeq") int boardSeq) throws Exception {

        Map<String, Object> response = new HashMap<>();
        try {
            BoardVO boardDetail = boardService.getOne(boardSeq);
            List<ChangedBoardVO> changedBoardVOList = boardService.getChangedBoardList(boardSeq);

            response.put("boardDetail", boardDetail);
            response.put("changedBoardList", changedBoardVOList);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
