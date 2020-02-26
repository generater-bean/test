package li.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import li.mapper.SqlDao;
import li.mapper.SqlSessionmapper;
import li.model.User;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {
	@Autowired
	private SqlDao sqlDao;
	/**
	 * 第三方登录
	 * @param user
	 */
	public void createOrUpdate(User user) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		User  dbUser=sqlDao.selectByAccountId(user.getAccountId());
		if(dbUser==null	) {
			//插入
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			sqlDao.addUser(user);
		}else {
			//更新
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setToken(user.getToken());
			dbUser.setName(user.getName());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			sqlDao.update(dbUser);
		}
	}
	/**
	 * localUserlogin
	 * @param user
	 * @return
	 */
	public User localUserLogin(User user) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		User  dbUser=sqlDao.selectByNameAndAccountId(user);
		return dbUser;
	}
	/**
	 * addRegister
	 * @param user
	 */
	public boolean addRegister(User user) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		User  dbUser=sqlDao.selectByName(user.getName());
		if(dbUser==null	) {
			//插入
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			sqlDao.addUser(user);
			return true;
		}else {
			return false;
		}
		
	}
  /**
   * 更新用户资料
   * @param user
   */
	public void updateUserInfo(User user) {
		// TODO Auto-generated method stub
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		sqlDao.updateUserInfo(user);
	}
}
