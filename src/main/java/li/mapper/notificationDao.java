package li.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import li.model.Notification;
import li.model.Profile;
import li.model.Question;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class notificationDao {
	public SqlSession session = null;
	public notificationDao(SqlSession sqlSession){
		this.session = sqlSession;
	}
	public void insert(Notification notification) {
		session.insert("li.mapper.notification.insert",notification);
		session.commit();
	}
	public int acountByReceiver(long receiver) {
		Integer totalcount=0;
		totalcount	=session.selectOne("li.mapper.notification.acountByReceiver",receiver);
		
		return	totalcount;
		
	}
	public int acountByReceiverStatus(Notification notification) {
		Integer totalcount=0;
		totalcount	=session.selectOne("li.mapper.notification.acountByReceiverStatus",notification);
		
		return	totalcount;
		
	}
	public List<Notification> selectByReceiver(Profile profile) {
		List<Notification> notifications=null;
		try {
			notifications = session.selectList("li.mapper.notification.selectByReceiver",profile);
		} catch (Exception e) {
			
		}
		
		return notifications;
	}
	public Notification selectById(long id) {
		
		Notification notification=	session.selectOne("li.mapper.notification.selectById",id);
		return notification;
	}
	public void updateById(Notification notification) {
		session.update("li.mapper.notification.updateById", notification);
		session.commit();
	}
	
}
