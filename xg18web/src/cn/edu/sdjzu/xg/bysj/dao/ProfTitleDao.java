package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;

import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public final class ProfTitleDao {
    private static ProfTitleDao profTitleDao = new ProfTitleDao();
    private static Collection<ProfTitle> profTitles;

    static {
        profTitles = new TreeSet<ProfTitle>();
        ProfTitle ProfTitle = new ProfTitle(1, "教授", "01", "");
        profTitles.add(ProfTitle);
        profTitles.add(new ProfTitle(2, "副教授", "02", ""));
        profTitles.add(new ProfTitle(3, "讲师", "03", ""));
        profTitles.add(new ProfTitle(4, "助教", "04", ""));
    }

    private ProfTitleDao() {
    }

    public static ProfTitleDao getInstance() {
        return profTitleDao;
    }

    public Collection<ProfTitle> findAll()throws SQLException{
        return ProfTitleDao.profTitles;
    }

    public ProfTitle find(Integer id)throws SQLException{
        ProfTitle desiredProfTitle = null;
        for (ProfTitle profTitle : profTitles){
            if (id.equals(profTitle.getId())) {
                desiredProfTitle = profTitle;
                break;
            }
        }
        return desiredProfTitle;
    }

    public boolean update(ProfTitle profTitle)throws SQLException{
        profTitles.remove(profTitle);
        return profTitles.add(profTitle);
    }

    public boolean add(ProfTitle profTitle)throws SQLException {
        return profTitles.add(profTitle);
    }

    public boolean delete(Integer id)throws SQLException{
        ProfTitle profTitle = this.find(id);
        return this.delete(profTitle);
    }

    public boolean delete(ProfTitle profTitle)throws SQLException{
        return profTitles.remove(profTitle);
    }
}

