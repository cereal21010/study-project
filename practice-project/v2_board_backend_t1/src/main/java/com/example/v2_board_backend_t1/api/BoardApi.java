package com.example.v2_board_backend_t1.api;

import com.example.v2_board_backend_t1.service.BoardService;
import com.example.v2_board_backend_t1.vo.BoardCommentVO;
import com.example.v2_board_backend_t1.vo.BoardVO;
import com.example.v2_board_backend_t1.vo.MemberVO;
import com.example.v2_board_backend_t1.vo.SearchVO;
import com.example.v2_board_backend_t1.vo.response.ResponseBoardVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardApi {

    final private BoardService boardService;

    @RequestMapping(value = "/test/{seq}", method = RequestMethod.PUT)
    public void updateTest(@RequestPart(value = "responseBody") BoardVO boardVO
                        , @RequestPart(value = "files", required = false) List<MultipartFile> files
                        , @PathVariable("seq") int seq) {

        log.info("-- api updateTest --");

        boardVO.setSeq(seq);
        BoardVO findBoard = boardService.findBySeq(seq);

    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getBoardList(SearchVO searchVO) {

        log.info("-- api getBoardList --");

        HashMap<String, Object> responseMap = new HashMap<>();

        List<BoardVO> boardList = boardService.getBoardList(searchVO);

        responseMap.put("boardList", boardList);

        return new Result(responseMap);
    }

    @RequestMapping(value = "/detail/{seq}", method = RequestMethod.GET)
    public Result getBoardDetail(@PathVariable(value = "seq") int boardSeq) {

        log.info("-- api getBoardDetail --");

        HashMap<String, Object> responseMap = new HashMap<>();
        BoardVO boardDetail = boardService.getBoard(boardSeq);
        boardService.increaseViewCount(boardSeq);
        responseMap.put("boardDetail", boardDetail);

        return new Result(responseMap);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insertBoard(@RequestPart(value = "responseBody") BoardVO boardVO
                            , @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        log.info("-- api insertBoard --");

//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        List<MultipartFile> files = multipartRequest.getFiles("files");

        boardService.insertBoard(boardVO, files);
        int boardSeq = boardVO.getSeq();

        boardService.storeFiles(boardVO.getSeq(), files);

        return new Result(boardSeq);
    }

    @RequestMapping(value = "/update/{seq}", method = RequestMethod.PUT)
    public Result updateBoard(@RequestPart(value = "responseBody") BoardVO boardVO
                            , @RequestPart(value = "files", required = false) List<MultipartFile> files
                            , @RequestPart(value = "deleteFileSeqs", required = false) List<Integer> deleteFileSeqs
                            , @PathVariable("seq") int boardSeq) {

        log.info("-- api updateBoard --");


        boardVO.setSeq(boardSeq);
        BoardVO beforeBoard = boardService.getBoard(boardSeq);
        boardService.updateBoardFiles(boardSeq, deleteFileSeqs, files);
        boardService.updateBoard(boardVO);
        boardService.insertChangeBoard(beforeBoard);

        return new Result(boardSeq);
    }

    @RequestMapping(value = "/delete/{seq}", method = RequestMethod.DELETE)
    public Result deleteBoard(@PathVariable(value = "seq") int seq) {

        log.info("-- api deleteBoard --");

        BoardVO boardVO = boardService.findBySeq(seq);
        boardService.deleteBoard(seq);

        return new Result("");
    }

    @RequestMapping(value = "/downloadFile/{seq}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("seq") int seq) {

        boardService.downloadFile(response, seq);

//        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/comment/insert", method = RequestMethod.POST)
    public Result insertBoardComment(@RequestBody BoardCommentVO boardCommentVO) {

        log.info("-- api insertBoardComment --");

        boardService.insertComment(boardCommentVO);

        return new Result(boardCommentVO.getSeq());
    }

    @RequestMapping(value = "/comment/delete/{seq}", method = RequestMethod.DELETE)
    public Result deleteBoardComment(@PathVariable(value = "seq") int seq) {

        log.info("-- api deleteBoardComment --");

        List<Integer> deletedSeqs = boardService.deleteBoardComment(seq);

        return new Result(deletedSeqs);
    }

    @RequestMapping(value = "/password/check/{seq}", method = RequestMethod.POST)
    public Result checkBoardPassword(@RequestBody String inputPassword
                                    , @PathVariable(value = "seq") int boardSeq ) {

        boolean result = boardService.checkBoardPassword(boardSeq, inputPassword);

        return new Result(result);
    }

    @RequestMapping(value = "/like/{seq}")
    public Result LikeBoard(@PathVariable("seq") int seq
                            , @RequestParam(value = "memberId") String memberId) {

//        memberVO.setMemberId("user01"); //test data

        boardService.likeBoard(seq, memberId);

        return new Result("");
    }

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
    }



}
