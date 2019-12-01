package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;


public final class UserDao {
    private static UserDao userDao = new UserDao();
    private static Collection<User> users;

    static {
        TeacherDao teacherDao = TeacherDao.getInstance();
        users = new TreeSet<User>();
        User user = null;
        try {
            user = new User(1, "st", "st", new Date(), teacherDao.find(1));
            users.add(user);
            users.add(new User(2, "lx", "lx", new Date(), teacherDao.find(2)));
            users.add(new User(3, "wx", "wx", new Date(), teacherDao.find(3)));
            users.add(new User(4, "lf", "lf", new Date(), teacherDao.find(4)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserDao() {
    }

    public static UserDao getInstance() {
        return userDao;
    }

    public static void main(String[] args) throws SQLException {
        UserDao dao = new UserDao();
        Collection<User> users = dao.findAll();
        display(users);
    }

    private static void display(Collection<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public Collection<User> findAll() throws SQLException{
        return UserDao.users;
    }

    public User find(Integer id) throws SQLException{
        User desiredUser = null;
        for (User user : users) {
            if (id.equals(user.getId())) {
                desiredUser = user;
                break;
            }
        }
        return desiredUser;
    }

    public boolean update(User user) throws SQLException{
        users.remove(user);
        return users.add(user);
    }

    public boolean add(User user) throws SQLException{
        return users.add(user);
    }

    public boolean delete(Integer id) throws SQLException{
        User user = this.find(id);
        return this.delete(user);
    }

    public boolean delete(User user) throws SQLException{
        return users.remove(user);
    }


}
