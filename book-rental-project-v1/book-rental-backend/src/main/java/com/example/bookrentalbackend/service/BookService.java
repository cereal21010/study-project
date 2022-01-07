package com.example.bookrentalbackend.service;

import com.example.bookrentalbackend.mapper.BookMapper;
import com.example.bookrentalbackend.util.FileHandler;
import com.example.bookrentalbackend.vo.BookFileVO;
import com.example.bookrentalbackend.vo.search.BookSearchVO;
import com.example.bookrentalbackend.vo.BookVO;
import com.example.bookrentalbackend.vo.search.RentalSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        BookVO bookVO = bookMapper.findBookBySeq(bookSeq);
//        List<BookFileVO> bookFileVOS = bookMapper.getBookFileList( Map.of("bookSeq", bookSeq) );
//        bookVO.setBookFiles(bookFileVOS);

        return bookVO;
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
        bookMapper.deleteBook(bookSeq);

        List<BookFileVO> bookFileVOS = bookMapper.getBookFileList(Map.of("bookSeq", bookSeq));
        _deleteFiles(bookFileVOS);
    }

    private void _storeFiles(long bookSeq, List<MultipartFile> files) throws IOException {
        List<BookFileVO> bookFileVOS = fileHandler.parseFileInfo(bookSeq, files);

        for (BookFileVO bookFileVO : bookFileVOS) {
            bookMapper.insertBookFile(bookFileVO);
        }
    }

    private void _deleteFiles(List<BookFileVO> bookFileVOS) {
        for (BookFileVO bookFileVO : bookFileVOS) {
            bookMapper.deleteBookFile(bookFileVO.getSeq());
        }
        fileHandler.deleteBookFile(bookFileVOS);
    }

}
