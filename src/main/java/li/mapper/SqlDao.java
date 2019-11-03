package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import li.dto.QuestionQueryDTO;
import li.model.Profile;
import li.model.Question;
import li.model.User;
@Component

public class SqlDao {
	public SqlSession session = null;
	public SqlDao(SqlSession sqlSession){
		this.session = sqlSession;
	}
	
	public void addTitle(Question question){
		session.insert("li.mapper.sql.addTitle", question);
		session.commit();
		
	}
	public java.util.List<Question> selectAll(Profile profile){
		java.util.List<Question> questionList=null;
		try {
			questionList = session.selectList("li.mapper.sql.selectAll",profile);
		} catch (Exception e) {
			
		}
		
		return questionList;
	}
	public java.util.List<Question> selectProfile(Profile profile){
		java.util.List<Question> questionList=null;
		try {
			questionList = session.selectList("li.mapper.sql.selectProfile",profile);
		} catch (Exception e) {
			
		}
	
		return questionList;
	}
	public Integer acount() {
		Integer totalcount	=session.selectOne("li.mapper.sql.count");
		
		return	totalcount;
		
	}
	public Integer acountById(long userId) {
		Integer totalcount	=session.selectOne("li.mapper.sql.acountById",userId);
		
		return	totalcount;
		
	}
	public User selectById(long id){
		User user=session.selectOne("li.mapper.sql.selectById", id);
		
		return user;
	}
	public List<User> selectByUsersId(List<Long> userIds) {
		List<User> users=session.selectList("li.mapper.sql.selectByUsersId", userIds);
		return users;
	}
	public void addUser(User user){
		session.insert("li.mapper.sql.addUser", user);
		session.commit();
		
	}
	public User selectByToken(String token){
		User user=session.selectOne("li.mapper.sql.selectByToken", token);
		
		return user;
	}

	public Question getById(long id) {
		
		Question question=null;
		try {
			question = session.selectOne("li.mapper.sql.getById",id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return question;
	}

	public User selectByAccountId(String accountId) {
		User user=session.selectOne("li.mapper.sql.selectByAccountId", accountId);
		return user;
	}

	public void update(User user) {
		session.update("li.mapper.sql.updateUser", user);
		session.commit();
		
	}

	public  int questionUpdate(Question question) {
		int update=session.update("li.mapper.sql.questionUpdate", question);
		session.commit();
		return update;
	}
	public  int incViewCount(long id) {
		int update=session.update("li.mapper.sql.updateViewCount", id);
		session.commit();
		return update;
	}
	public  int incCommentCount(long id) {
		int update=session.update("li.mapper.sql.updateCommetnCount", id);
		session.commit();
		return update;
	}
	public List<Question> selectRelated(Question question){
		List<Question> questions= session.selectList("li.mapper.sql.selectRelated", question);
		return questions;
	}

	public Integer acountBySearch(QuestionQueryDTO questionQueryDTO) {
		Integer totalcount	=session.selectOne("li.mapper.sql.acountBySearch",questionQueryDTO);
		return totalcount;
	}

	public List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO) {
		List<Question> questions= session.selectList("li.mapper.sql.selectBySearch", questionQueryDTO);
		return questions;
	}

	public User selectByNameAndAccountId(User user) {
		User dbuser=session.selectOne("li.mapper.sql.selectByNameAndAccountId", user);
		return dbuser;
	}
	
}
