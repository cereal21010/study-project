package dao;

import java.util.List;

import vo.SampleVo;

public interface SampleDao {
	List<SampleVo> getMetainfo(final int seq);
}
