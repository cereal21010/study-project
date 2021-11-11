package com.example.v2_board_backend_t1.service;

import com.example.v2_board_backend_t1.mapper.BoardMapper;
import com.example.v2_board_backend_t1.util.FileHandler;
import com.example.v2_board_backend_t1.vo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    final private BoardMapper boardMapper;

    final private FileHandler fileHandler;

    public List<BoardVO> getAll() {
        return boardMapper.getAll();
    }

    public List<BoardVO> getBoardList(SearchVO searchVO) {
        return boardMapper.getBoardList(searchVO);
    }

    public BoardVO findBySeq(int seq){
        return boardMapper.findBySeq(seq);
    }

    public BoardVO getBoard(int boardSeq) {

        BoardVO findBoard = boardMapper.findBySeq(boardSeq);
        List<BoardFileVO> boardFiles = boardMapper.getBoardFiles( Map.of("boardSeq", boardSeq) );
        findBoard.setFiles(boardFiles);
        List<BoardCommentVO> boardComments = boardMapper.getBoardComments( Map.of("boardSeq", boardSeq) );
        findBoard.setComments(boardComments);

        return findBoard;
    }

    public int insertBoard(BoardVO vo, List<MultipartFile> files) {
        return boardMapper.insertBoard(vo);
    }

    public BoardVO updateBoard(BoardVO boardVO) {

        BoardVO targetBoard = boardMapper.findBySeq(boardVO.getSeq());

        _overrideNotEmptyValues(boardVO, targetBoard);

        boardMapper.updateBoard(targetBoard);
        return targetBoard;
    }

    private void _overrideNotEmptyValues(BoardVO from, BoardVO to) {
        final String[] ignoreFields = {"seq"};

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(from.getClass(), Object.class);
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

                if (descriptor.getReadMethod() != null) {
                    Object fromValue = descriptor.getReadMethod().invoke(from);

                    if ( descriptor.getWriteMethod() == null
                            || descriptor.getReadMethod() == null
                            || fromValue == null ) {
                        continue;
                    }

                    if (fromValue != null && !Arrays.stream(ignoreFields).anyMatch(descriptor.getName()::equals)) {
                        if (fromValue instanceof Number) {
                            if (((Number) fromValue).intValue() <= 0) {
                                continue;
                            }
                        }
                    }
                    descriptor.getWriteMethod().invoke(to, fromValue);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void updateBoardFiles(int boardSeq, List<Integer> deleteFileSeqs, List<MultipartFile> files) {

        if (deleteFileSeqs != null) {
            List<BoardFileVO> boardFiles = boardMapper.getBoardFiles(Map.of("seqs", deleteFileSeqs));
            _deleteFiles(boardFiles);
        }

        if (files != null) {
            storeFiles(boardSeq, files);
        }

    }

    private List<Integer> _deleteFiles(List<BoardFileVO> boardfiles) {

        fileHandler.deleteBoardFile(boardfiles);

        List<Integer> seqs = boardfiles
                .stream()
                .map(boardfile -> boardfile.getSeq())
                .collect(Collectors.toList());

        boardMapper.deleteBoardFile(Map.of("seqs", seqs));
        
        return seqs;
    }


    public void deleteBoard(int boardSeq) {

        List<BoardFileVO> boardFiles = boardMapper.getBoardFiles(Map.of("boardSeq", boardSeq));
        List<BoardCommentVO> boardComments = boardMapper.getBoardComments(Map.of("boardSeq", boardSeq));

        _deleteFiles(boardFiles);
        _deleteComments(boardComments);
        
        boardMapper.deleteBoard(boardSeq);

    }

    public void increaseViewCount(int seq) {
        boardMapper.increaseViewCount(seq);
    }


    public void storeFiles(int seq, List<MultipartFile> files) {
        List<BoardFileVO> boardFileVOS = _storeFiles(seq, files);

        for (BoardFileVO vo : boardFileVOS) {
            boardMapper.insertBoardFile(vo);
        }

    }

    private List<BoardFileVO> _storeFiles(int boardSeq, List<MultipartFile> multipartFiles) {
        List<BoardFileVO> boardFileVOS = new ArrayList<>();
        try {
            boardFileVOS = fileHandler.parseFileInfo(boardSeq, multipartFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boardFileVOS;
    }

    public void downloadFile(HttpServletResponse response, int boardFileSeq) {

        try {
            List<Integer> seqs = Arrays.asList(boardFileSeq);
            List<BoardFileVO> boardFileVOS = boardMapper.getBoardFiles( Map.of("seqs",seqs));
            BoardFileVO vo = null;
            
            //boardFileVOS null check
            if (boardFileVOS.size() <= 0) {
                log.error("not find file.");
                return; // (수정) not find error로 변경
            } else {
                vo = boardFileVOS.get(0);
            }

            String fileName = new String(vo.getSaveName().toString().getBytes("utf-8"), "iso-8859-1");
//            String orgFileName = new String( vo.getOriginalName().toString().getBytes("utf-8"), "iso-8859-1" );
            String orgFileName = URLEncoder.encode(vo.getOriginalName(), "utf-8");

            File file = new File("C:\\Users\\tlduf\\workspace\\study-project\\practice-project\\v2_board_backend_t1\\file_dir", vo.getSaveName());
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + orgFileName + "\"");
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int insertComment(BoardCommentVO boardCommentVO) {
        return boardMapper.insertBoardComment(boardCommentVO);
    }

    public List<Integer> deleteBoardComment(int seq) {
        List<Integer> seqs = Arrays.asList(seq);
        List<BoardCommentVO> comments = boardMapper.getBoardComments(Map.of("seqs", seqs));
        List<Integer> deletedSeqs = _deleteComments(comments);
        return deletedSeqs;
    }

    private List<Integer> _deleteComments(List<BoardCommentVO> comments) {

        List<Integer> seqs = comments
                .stream()
                .map(comment -> comment.getSeq())
                .collect(Collectors.toList());

        boardMapper.deleteBoardComment(Map.of("seqs", seqs));

        return seqs;
    }

    public boolean checkBoardPassword(int boardSeq, String inputPassword) {

        BoardVO findBaord = boardMapper.findBySeq(boardSeq);

        return inputPassword.equals(findBaord.getPassword()) ? true : false;
    }

    public void likeBoard(int seq, String memberId) {
        BoardVO findBoard = boardMapper.findBySeq(seq);

        // (수정) 회원 아이디로 회원을 조회 후 board_like 테이블에 insert 하도록 수정!
//        MemberVO findMember = memberMapper.findById(memberId);

        // 게시글 삭제 시 삭제된 게시글 관련된 board_like 데이터도 모두 함께 삭제! ( fk가 cascade여서 자동으로 삭제 되나? )
        Map <String, Object> paramMap = Map.of("boardSeq", findBoard.getSeq(), "memberSeq", 1);
        boardMapper.insertLikeBoard(paramMap);
    }

    public void insertChangeBoard(BoardVO boardVO) {

        ChangeBoardVO changeBoardVO = ChangeBoardVO.builder()
                                                .title(boardVO.getTitle())
                                                .content(boardVO.getContent())
                                                .writer(boardVO.getWriter())
                                                .category(boardVO.getCategory())
                                                .originBoardSeq(boardVO.getSeq())
                                                .build();

        boardMapper.insertChangeBoard(changeBoardVO);
    }
}
