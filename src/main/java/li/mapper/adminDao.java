package li.mapper;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
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
	public void deleteQuestion(long id) {
		// TODO Auto-generated method stub
		session.delete("li.mapper.admin.deleteQuestion", id);
		session.commit();
	}
	public void deleteRole(long id) {
		// TODO Auto-generated method stub
		session.delete("li.mapper.admin.deleteRole", id);
		session.commit();
	}
	public void LimitQuestion(Question question) {
		// TODO Auto-generated method stub
		session.update("li.mapper.admin.LimitQuestion", question);
		session.commit();
	}
	public int selectLikeCount(long id) {
		// TODO Auto-generated method stub
		int likeCount=session.selectOne("li.mapper.admin.selectLikeCount", id);
		
		return likeCount;
		
	}

}
