package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import li.model.DialogInfo;
import li.model.Profile;
import li.model.User;
@Component
public class dialogDao {
	public SqlSession session = null;
	public dialogDao(SqlSession sqlSession){
		this.session = sqlSession;
	}
	public void insertDialog(DialogInfo dialog) {
		// TODO Auto-generated method stub
		session.insert("li.mapper.dialog.insertDialog", dialog);
		session.commit();
	}
	public List<User> selectUser(Profile profile) {
		List<User> dbuser=session.selectList("li.mapper.dialog.selectUser", profile);
		return dbuser;
	}
	public Integer acountUser() {
		// TODO Auto-generated method stub
		Integer totalcount	=session.selectOne("li.mapper.dialog.acountUser");
		return totalcount;
	}
	public List<DialogInfo> selectByStatus(Profile profile) {
		// TODO Auto-generated method stub
		 List<DialogInfo>  dialogs	=session.selectList("li.mapper.dialog.selectByStatus",profile);
		return dialogs;
	}
	public Integer acountStatus() {
		Integer totalcount	=session.selectOne("li.mapper.dialog.acountStatus");
		return totalcount;
	}
	public User selectById(long id) {
		// TODO Auto-generated method stub
		User dbuser=session.selectOne("li.mapper.dialog.selectById", id);
		return dbuser;
		
	}
	public long accoutByIdAndStatus(DialogInfo dialogInfo) {
		// TODO Auto-generated method stub
		long totalcount	=session.selectOne("li.mapper.dialog.accoutByIdAndStatus",dialogInfo);
		return totalcount;
	}
	public void updateStatus(DialogInfo dialogInfo) {
		// TODO Auto-generated method stub
		session.update("li.mapper.dialog.updateStatus", dialogInfo);
		session.commit();
	}
	
	
}
