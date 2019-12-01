package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Degree;

import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public final class DegreeDao {
    private static DegreeDao degreeDao =
            new DegreeDao();
    private static Collection<Degree> degrees;

    static {
        degrees = new TreeSet<Degree>();
        Degree doctor = new Degree(1,
                "博士", "01", "");
        degrees.add(doctor);
        degrees.add(new Degree(2,
                "硕士", "02", ""));
        degrees.add(new Degree(3,
                "学士", "03", ""));
    }

    private DegreeDao() {
    }

    public static DegreeDao getInstance() {
        return degreeDao;
    }

    public Collection<Degree> findAll() throws SQLException {
        return DegreeDao.degrees;
    }

    public Degree find(Integer id) throws SQLException {
        Degree desiredDegree = null;
        for (Degree degree : degrees) {
            if (id.equals(degree.getId())) {
                desiredDegree = degree;
                break;
            }
        }
        return desiredDegree;
    }

    public boolean update(Degree degree) throws SQLException {
        degrees.remove(degree);
        return degrees.add(degree);
    }

    public boolean add(Degree degree) throws SQLException {
        return degrees.add(degree);
    }

    public boolean delete(Integer id) throws SQLException {
        Degree degree = this.find(id);
        return this.delete(degree);
    }

    public boolean delete(Degree degree) throws SQLException {
        return degrees.remove(degree);
    }
}

