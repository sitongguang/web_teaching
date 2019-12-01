package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public final class DepartmentDao {
    private static Collection<Department> departments;
    private static DepartmentDao departmentDao = new DepartmentDao();

    static {
        School managementSchool = null;
        School civilSchool = null;

        try {
            managementSchool = SchoolDao.getInstance().find(2);
            departments = new TreeSet<Department>();
            Department department = new Department(1, "工程管理", "0201", "",
                    managementSchool);
            departments.add(department);
            departments
                    .add(new Department(2, "信息管理", "0202", "", managementSchool));
            departments
                    .add(new Department(3, "工程造价", "0203", "", managementSchool));
            departments
                    .add(new Department(4, "工业工程", "0204", "", managementSchool));

            civilSchool = SchoolDao.getInstance().find(1);

            Department civilDepartment = new Department(11, "土木工程", "0101", "",
                    civilSchool);
            departments.add(civilDepartment);
            departments
                    .add(new Department(12, "岩土工程", "0102", "", civilSchool));


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        return departmentDao;
    }


    public Collection<Department> findAll() throws SQLException {
        return DepartmentDao.departments;
    }
    public Collection<Department> findAllBySchool(Integer schoolId) throws SQLException {
        Collection<Department> desiredDepartments = new HashSet<Department>();
        for (Department department : departments) {
            if (schoolId.equals(department.getSchool().getId())) {
                desiredDepartments.add(department);
            }
        }
        return desiredDepartments;
    }

    public Department find(Integer id) throws SQLException {
        Department desiredDepartment = null;
        for (Department department : departments) {
            if (id.equals(department.getId())) {
                desiredDepartment = department;
                break;
            }
        }
        return desiredDepartment;
    }

    public boolean update(Department department) throws SQLException {
        departments.remove(department);
        return departments.add(department);
    }

    public boolean add(Department department) throws SQLException {
        return departments.add(department);
    }

    public boolean delete(Integer id) throws SQLException {
        Department department = this.find(id);
        return this.delete(department);
    }

    public boolean delete(Department department) throws SQLException {
        return departments.remove(department);
    }
}

