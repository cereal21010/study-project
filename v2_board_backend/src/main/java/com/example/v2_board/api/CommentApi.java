package com.example.v2_board.api;

import com.example.v2_board.service.CommentService;
import com.example.v2_board.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Slf4j
public class CommentApi {

    private final CommentService commentService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<?> insertComment(@RequestPart("requestBody") CommentVO vo) {

//        vo.setWriter("user01");   //test data

        try {
            commentService.insertCommnet(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateComment(@RequestPart("requestBody") CommentVO vo) {

        try {
            commentService.updateComment(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable("seq") int seq) {

        try {
            commentService.deleteComment(seq);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
