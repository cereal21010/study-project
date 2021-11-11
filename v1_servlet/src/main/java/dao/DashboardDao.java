package dao;

import java.util.List;

import utills.page.PageMaker;
import vo.DashboardVO;

public interface DashboardDao {

    public List<DashboardVO> findAll();

    public DashboardVO findBySeq(int seq);

    public int selectCount();

    public List<DashboardVO> selectBoard(PageMaker pm);

}
