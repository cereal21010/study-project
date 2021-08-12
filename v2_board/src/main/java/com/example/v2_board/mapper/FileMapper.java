package com.example.v2_board.mapper;

import com.example.v2_board.dto.FileDTO;

import java.util.List;

public interface FileMapper {
    public List<FileDTO> selectList(int BoardSeq);
    public void insert(FileDTO dto);
    public FileDTO getOne(int seq);
}
