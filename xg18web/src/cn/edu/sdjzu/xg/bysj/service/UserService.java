package cn.edu.sdjzu.xg.bysj.service;


import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.User;

import java.sql.SQLException;
import java.util.Collection;

public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}
	
	public static UserService getInstance(){
		return UserService.userService;
	}

	public Collection<User> findAll() throws SQLException {
		return userDao.findAll();
	}
	
	public User find(Integer id) throws SQLException {
		return userDao.find(id);
	}

	/**
	 * 不能修改用户名
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean update(User user) throws SQLException {
		userDao.delete(user);
		return userDao.add(user);
	}
	
	public boolean add(User user) throws SQLException {
		return userDao.add(user);
	}

	public boolean delete(Integer id) throws SQLException {
		User user = this.find(id);
		return this.delete(user);
	}
	
	public boolean delete(User user) throws SQLException {
		return userDao.delete(user);
	}

	/**
	 * 登录
	 * @param userToLogin 前台传送过来的User对象
	 * @return
	 * @throws SQLException
	 */
	public User login(User userToLogin) throws SQLException {
		return this.login(userToLogin.getUsername(),userToLogin.getPassword());
	}

	/**
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录成功的User对象
	 * @throws SQLException
	 */
	public User login(String username, String password) throws SQLException {
		Collection<User> users = this.findAll();
		User desiredUser = null;
		for(User user:users){
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
				desiredUser = user;
				break;
			}
		}
		return this.login(desiredUser);
	}	
}
