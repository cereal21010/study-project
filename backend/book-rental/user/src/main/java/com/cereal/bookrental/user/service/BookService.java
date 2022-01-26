package com.cereal.bookrental.user.service;

import com.cereal.bookrental.user.exception.ApiException;
import com.cereal.bookrental.user.exception.ExceptionEnum;
import com.cereal.bookrental.user.mapper.BookMapper;
import com.cereal.bookrental.user.util.FileHandler;
import com.cereal.bookrental.user.vo.BookFileVO;
import com.cereal.bookrental.user.vo.BookVO;
import com.cereal.bookrental.user.vo.search.BookSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final FileHandler fileHandler;

    public List<BookVO> getAll() {
        return bookMapper.getAll();
    }

    public List<BookVO> getBookList(BookSearchVO bookSearchVO) {
        return bookMapper.getBookList(bookSearchVO);
    }

    public int getBookCount(BookSearchVO bookSearchVO) {
        return bookMapper.getBookCount(bookSearchVO);
    }

    public BookVO getBookDetail(long bookSeq) {
        return  bookMapper.findBookBySeq(bookSeq);
    }

    /**
     * 이미지 출력에 필요한 파일의 byte[] 와 HttpHeaders를 Map에 담아서 반환
     * @param bookFileSeq
     * @return Map{image: byte[], headers: HttpHeaders}
     * @throws IOException
     */
    public Map<String, Object> getImageByte(long bookFileSeq) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();

        List<BookFileVO> bookFileVOS = bookMapper.getBookFileList( Map.of("seqs", Arrays.asList(bookFileSeq)) );
        if (bookFileVOS != null) {//TODO: collection util 사용에서 조회 여부 체크하기
            resultMap.put( "image", fileHandler.imagePrint(bookFileVOS.get(0)) );
            resultMap.put( "headers", fileHandler.getHeaders(bookFileVOS.get(0)) );
        } else {
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
        return resultMap;
    }

    public List<BookFileVO> getBookFileList(long bookSeq) {
        return bookMapper.getBookFileList( Map.of("bookSeq", bookSeq) );
    }

    public void insertBook(BookVO bookVO, List<MultipartFile> files) throws IOException {
        bookMapper.insertBook(bookVO);
        if (files != null && bookVO.getSeq() > 0) {
            _storeFiles(bookVO.getSeq(), files);
        }
    }

    public void updateBook(BookVO bookVO) throws IOException {
        bookMapper.updateBook(bookVO);
    }

    public void updateBookFile(long bookSeq, List<MultipartFile> files, List<Long> deleteFileSeqs) throws IOException {
        if ( files != null ) {
            _storeFiles(bookSeq, files);
        }

        if( !CollectionUtils.isEmpty(deleteFileSeqs) ) {
            List<BookFileVO> bookFileVOS = bookMapper.getBookFileList( Map.of("seqs", deleteFileSeqs) );
            _deleteFiles(bookFileVOS);
        }
    }

    public void deleteBook(long bookSeq) {
        
        List<BookFileVO> bookFileVOS = bookMapper.getBookFileList(Map.of("bookSeq", bookSeq));
        //파일, 파일정보 삭제
        _deleteFiles(bookFileVOS);
        //도서정보 삭제
        bookMapper.deleteBook(bookSeq);
    }

    private void _storeFiles(long bookSeq, List<MultipartFile> files) throws IOException {
        List<BookFileVO> bookFileVOS = fileHandler.parseFileInfo(bookSeq, files);

        for (BookFileVO bookFileVO : bookFileVOS) {
            bookMapper.insertBookFile(bookFileVO);
        }
    }

    private void _deleteFiles(List<BookFileVO> bookFileVOS) {
        /* DB 데이터와 파일이 한 묶음으로 삭제될 수 있도록 한다. */
        for (BookFileVO bookFileVO : bookFileVOS) {
            bookMapper.deleteBookFile(bookFileVO.getSeq());
            fileHandler.deleteBookFile(bookFileVO);
        }
    }

}
