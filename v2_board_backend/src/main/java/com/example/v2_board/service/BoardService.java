package com.example.v2_board.service;

import com.example.v2_board.mapper.ChangedBoardMapper;
import com.example.v2_board.vo.BoardVO;
import com.example.v2_board.vo.ChangedBoardVO;
import com.example.v2_board.vo.SearchVO;
import com.example.v2_board.mapper.BoardMapper;
import com.example.v2_board.utills.PageMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private final ChangedBoardMapper changedBoardMapper;

    public List<BoardVO> getAll() throws Exception{
        return boardMapper.getAll();
    }

    public int totalCount(SearchVO vo) throws Exception{
        return boardMapper.getAllCount(vo);
    }

    public List<BoardVO> moreGetList(SearchVO vo) {
        return boardMapper.selectList(vo);
    }

    public List<BoardVO> selectList(SearchVO vo) throws Exception{
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("pageNum", pm.startList());
//        paramMap.put("contentNum", pm.getContentNum());
//        paramMap.put("searchType", searchType);
//        paramMap.put("keyword", keyword);
        vo.setStartContentNum( (vo.getPageNum()-1) * vo.getContentNum() );
        return boardMapper.selectList(vo);
    }

    public int insert(BoardVO vo) throws Exception{
        if(vo.getPassword() == null || vo.getPassword() == ""){
            vo.setPassword(null);
        }
        return boardMapper.insert(vo);
    }

    public BoardVO getOne(int seq) throws Exception{
        return boardMapper.getOne(seq);
    }

    public int delete(int seq) throws Exception{
        return boardMapper.delete(seq);
    }

    public int update(BoardVO vo) throws Exception{
        return boardMapper.update(vo);
    }

    public PageMaker getPageMaker(SearchVO vo) throws Exception{
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("searchType", searchType);
//        paramMap.put("keyword", keyword);
        return PageMaker.createPageMaker( vo.getPageNum()
                                        , vo.getContentNum()
                                        , boardMapper.getAllCount(vo) );
    }

    /*public void addViewCount(BoardVO vo) {
        vo.setViewCount( vo.getViewCount() + 1 );
        boardMapper.addViewCount(vo);
    }*/

    public void increaseViewCount(int boardSeq) throws Exception {
        BoardVO vo = getOne(boardSeq);
        vo.setViewCount( vo.getViewCount() + 1 );
        boardMapper.increaseViewCount(vo);
    }

    public List<ChangedBoardVO> getChangedBoardList(int boardSeq){
        return changedBoardMapper.getBoardList(boardSeq);
    }

    public void insertChangedBoard(BoardVO boardVO){
        ChangedBoardVO changeVO = new ChangedBoardVO(boardVO);
        changedBoardMapper.insertChangedBoard(changeVO);
    }

}
