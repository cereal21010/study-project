package com.example.v2_board;

import com.example.v2_board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class V2BoardApplicationTests {

    @Autowired
    BoardService boardService;

    @Test
    void contextLoads() {

    }

    @Test
    @Transactional
    @Rollback(false)
    void dataInsert() throws Exception{

//        for(int i = 0; i < 100; i++) {
//            BoardDTO dto = new BoardDTO();
//            dto.setTitle("test title ..." +i);
//            dto.setContent("test content ..." +i);
//            dto.setWriterSeq(1);
//            dto.setWriter("user01");
//            dto.setCategory("free");
//            boardService.insert(dto);
//        }

    }

}
