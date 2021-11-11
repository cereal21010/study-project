package servlet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dao.DashboardDao;
import dao.SampleDao;
import session.SampleSessionFactory;
import vo.DashboardVO;
import vo.SampleVo;

public class SampleTest {

    public static void main(String[] args) {
        SampleSessionFactory fac = new SampleSessionFactory();
        SqlSession session = fac.openSession(false);

        DashboardDao mapper = session.getMapper(DashboardDao.class);

        List<DashboardVO> metainfo = mapper.findAll();
        System.out.println(metainfo);
    }

}
