package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import li.model.DialogInfo;
import li.model.Friend;
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
	public void insertFriend(Friend friend) {
		// TODO Auto-generated method stub
		session.insert("li.mapper.dialog.insertFriend",friend);
		session.commit();
	}
	public void resetFriend(Friend friend) {
		// TODO Auto-generated method stub
		session.insert("li.mapper.dialog.resetFriend",friend);
		session.commit();
	}
	
	public int acountFriend(long id) {
		// TODO Auto-generated method stub
		int totalcount	=session.selectOne("li.mapper.dialog.acountFriend",id);
		return totalcount;
		
	}
	public List<Friend> selectByUserId(Profile profile) {
		// TODO Auto-generated method stub
		List<Friend> friends=session.selectList("li.mapper.dialog.selectByUserId", profile);
		return friends;
	}
	public Friend selectByUserId(Friend friend) {
		// TODO Auto-generated method stub
		Friend friend1=session.selectOne("li.mapper.dialog.selectFriendByUserId", friend);
		
		return friend1;
		
		
		
	}
	
	
	
}
