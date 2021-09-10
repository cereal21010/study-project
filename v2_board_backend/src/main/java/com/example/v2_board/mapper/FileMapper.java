package com.example.v2_board.mapper;

import com.example.v2_board.vo.FileVO;

import java.util.List;

public interface FileMapper {
    public List<FileVO> selectList(int BoardSeq);
    public void insert(FileVO vo);
    public FileVO getOne(int seq);
    public void deleteOne(FileVO vo);
    public void deleteBoard(int boardSeq);
}
