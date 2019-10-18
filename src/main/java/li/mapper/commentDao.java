package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import li.dto.commentDTO;
import li.model.Comment;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class commentDao {
	public SqlSession session = null;
	public commentDao(SqlSession sqlSession){
		this.session = sqlSession;
	}
	public void insertComm(Comment comment) {
		
			session.insert("li.mapper.comment.insertComm", comment);
			session.commit();
			
		
		
	}
	public Comment selectById(long id) {
	
		Comment comment=session.selectOne("li.mapper.comment.selectById", id);
		return comment;
	}
	public Comment selectByParentId(long parentId) {
		
		Comment comment=session.selectOne("li.mapper.comment.selectByParentId", parentId);
		return comment;
	}
	public List<Comment> listByParentId(Comment comment) {
		
		List<Comment> comments=session.selectList("li.mapper.comment.listByParentId", comment);
		return comments;
	}
	public void incCommentCount(Comment comment) {
		session.update("li.mapper.comment.incCommentCount", comment);
		session.commit();
	}
}
