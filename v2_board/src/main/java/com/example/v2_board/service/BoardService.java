package com.example.v2_board.service;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.mapper.BoardMapper;
import com.example.v2_board.utills.PageMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardDTO> getAll() throws Exception{
        return boardMapper.getAll();
    }

    public List<BoardDTO> selectList(String searchType, String keyword, PageMaker pm) throws Exception{
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", pm.startList());
        paramMap.put("contentNum", pm.getContentNum());
        paramMap.put("searchType", searchType);
        paramMap.put("keyword", keyword);
        return boardMapper.selectList(paramMap);
    }

    public int insert(BoardDTO dto) throws Exception{
        return boardMapper.insert(dto);
    }

    public BoardDTO getOne(int seq) throws Exception{
        return boardMapper.getOne(seq);
    }

    public int delete(int seq) throws Exception{
        return boardMapper.delete(seq);
    }

    public int update(BoardDTO dto) throws Exception{
        return boardMapper.update(dto);
    }

    public PageMaker getPageMaker(String pageNum, String contentNum) throws Exception{
        return PageMaker.createPageMaker( Integer.parseInt(pageNum)
                                        , Integer.parseInt(contentNum)
                                        , boardMapper.getAllCount() );
    }

}
