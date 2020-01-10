package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import li.model.Comment;
import li.model.Profile;
import li.model.Question;
import li.model.User;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class adminDao {
	public SqlSession session = null;
	public adminDao(SqlSession sqlSession){
		this.session = sqlSession;
	}
	public Integer acountUser() {
		// TODO Auto-generated method stub
		Integer totalcount	=session.selectOne("li.mapper.admin.acountUser");
		return totalcount;
	}
	public List<User> selectUser(Profile profile) {
		List<User> dbuser=session.selectList("li.mapper.admin.selectUser", profile);
		return dbuser;
	}
	public Integer acount() {
		Integer totalcount	=session.selectOne("li.mapper.admin.count");
		
		return	totalcount;
		
	}
	public java.util.List<Question> selectAll(Profile profile){
		java.util.List<Question> questionList=null;
		try {
			questionList = session.selectList("li.mapper.admin.selectAll",profile);
		} catch (Exception e) {
			
		}
		
		return questionList;
	}
	public User selectById(long id){
		User user=session.selectOne("li.mapper.admin.selectById", id);
		
		return user;
	}
	
}
