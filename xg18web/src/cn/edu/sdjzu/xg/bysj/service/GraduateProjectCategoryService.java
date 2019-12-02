package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.GraduateProjectCategoryDao;
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectCategory;

import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectCategoryService {
	private GraduateProjectCategoryDao graduateProjectCategoryDao = GraduateProjectCategoryDao.getInstance();
	//本类的一个对象引用，保存自身对象
	private static GraduateProjectCategoryService graduateProjectCategoryService =  new GraduateProjectCategoryService();
	//私有的构造方法，防止其它类创建它的对象
	private GraduateProjectCategoryService(){}
	//静态方法，返回本类的惟一对象
	public synchronized static GraduateProjectCategoryService getInstance()throws SQLException {
		return graduateProjectCategoryService;
	}

	public Collection<GraduateProjectCategory> findAll()throws SQLException {
		return graduateProjectCategoryDao.findAll();
	}

	public GraduateProjectCategory find(Integer id)throws SQLException {
		return graduateProjectCategoryDao.find(id);
	}

	public boolean update(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		return graduateProjectCategoryDao.update(graduateProjectCategory);
	}

	public boolean add(GraduateProjectCategory graduateProjectCategory) throws SQLException{
		return graduateProjectCategoryDao.add(graduateProjectCategory);
	}

	public boolean delete(Integer id) throws SQLException{
		GraduateProjectCategory graduateProjectCategory = this.find(id);
		return this.delete(graduateProjectCategory);
	}

	public boolean delete(GraduateProjectCategory graduateProjectCategory) throws SQLException{
		return graduateProjectCategoryDao.delete(graduateProjectCategory);
	}
}
