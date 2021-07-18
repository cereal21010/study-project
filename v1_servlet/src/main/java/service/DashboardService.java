package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.DashboardDao;
import session.SampleSessionFactory;
import utills.page.PageMaker;
import vo.DashboardVO;

public class DashboardService {

	SampleSessionFactory fac = new SampleSessionFactory();
	SqlSession session = fac.openSession(false);
	
	DashboardDao mapper = session.getMapper(DashboardDao.class);
			
	public List<DashboardVO> getList(){
		return mapper.findAll();
	}
	
	public List<DashboardVO> selectBoard(int pageNum, int contentnum) {
		PageMaker pm = PageMaker.createPageMaker(pageNum, contentnum, mapper.selectCount());
		pm.setPageNum(pm.getPageNum()*10);
		return mapper.selectBoard(pm);
	}
	
	public DashboardVO getBoardVO(int seq) {
		return mapper.findBySeq(seq);
	}
	
	public PageMaker getPageMaker(int pageNum, int contentnum) {
		return PageMaker.createPageMaker(pageNum, contentnum, mapper.selectCount());
	}
	 
}
