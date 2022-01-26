package com.cereal.bookrental.user.mapper;

import com.cereal.bookrental.user.vo.BookFileVO;
import com.cereal.bookrental.user.vo.BookVO;
import com.cereal.bookrental.user.vo.search.BookSearchVO;

import java.util.List;
import java.util.Map;

public interface BookMapper {

    public List<BookVO> getAll();
    public List<BookVO> getBookList(BookSearchVO bookSearchVO);
    public int getBookCount(BookSearchVO bookSearchVO);
    public BookVO findBookBySeq(long bookSeq);
    public void insertBook(BookVO bookVO);
    public void updateBook(BookVO bookVO);
    public void deleteBook(long bookSeq);
    public List<BookFileVO> getBookFileList(Map paramMap);
    public void insertBookFile(BookFileVO bookFileVO);
    public void deleteBookFile(long bookFileSeq);

}
