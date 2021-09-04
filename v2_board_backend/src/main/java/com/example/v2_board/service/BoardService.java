package com.example.v2_board.service;

import com.example.v2_board.dto.BoardDTO;
import com.example.v2_board.dto.SearchDTO;
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

    public int totalCount(SearchDTO dto) throws Exception{
        return boardMapper.getAllCount(dto);
    }

    public List<BoardDTO> selectList(SearchDTO dto) throws Exception{
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("pageNum", pm.startList());
//        paramMap.put("contentNum", pm.getContentNum());
//        paramMap.put("searchType", searchType);
//        paramMap.put("keyword", keyword);
        dto.setStartContentNum( (dto.getPageNum()-1) * dto.getContentNum() );
        return boardMapper.selectList(dto);
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

    public PageMaker getPageMaker(SearchDTO dto) throws Exception{
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("searchType", searchType);
//        paramMap.put("keyword", keyword);
        return PageMaker.createPageMaker( dto.getPageNum()
                                        , dto.getContentNum()
                                        , boardMapper.getAllCount(dto) );
    }

}
