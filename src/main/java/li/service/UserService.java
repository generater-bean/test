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
	public User localUserLogin(User user) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		User  dbUser=sqlDao.selectByNameAndAccountId(user);
		return dbUser;
	}
	
}
