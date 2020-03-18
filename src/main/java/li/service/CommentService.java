package li.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import li.dto.commentDTO;
import li.enums.CommentTypeEnum;
import li.enums.NotificationStatusEnum;
import li.enums.NotificationTypeEnum;
import li.exception.CustomizeErrorCode;
import li.exception.CustomizeException;
import li.mapper.SqlDao;
import li.mapper.SqlSessionmapper;
import li.mapper.commentDao;
import li.mapper.notificationDao;
import li.model.Comment;
import li.model.Notification;
import li.model.Question;
import li.model.User;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class CommentService {
	@Autowired
	private commentDao commentDao;
	@Autowired
	private SqlDao sqlDao;
	@Autowired
	private notificationDao notificationDao;

	//@Transactional
	public void insertComm(Comment comment, User commentator) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);
		commentDao = new commentDao(s);
		notificationDao = new notificationDao(s);
		if (comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
		}
		
		
		
		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论
			Comment dbComment = commentDao.selectById(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FIND);
			}
			//
			Question question = sqlDao.getById(dbComment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentDao.insertComm(comment);
			// 增加评论数
			Comment updateComment = new Comment();
			updateComment.setId(comment.getParentId());
			commentDao.incCommentCount(updateComment);
			// 创建通知
			createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(),
					NotificationTypeEnum.REPLY_COMMENT, question.getId());
		} else if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
			// 回复问题
			Question question = sqlDao.getById(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentDao.insertComm(comment);
			sqlDao.incCommentCount(question.getId());
			// 创建通知
			createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(),
					NotificationTypeEnum.REPLY_QUESTION, question.getId());
		}

	}

	// 创建通知
	private void createNotify(Comment comment, long receiver, String notifierName, String outerTitle,
			NotificationTypeEnum notificationType, long outerId) {
		if(receiver==comment.getCommentator()) {
			return ;
		}
		Notification notification = new Notification();
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setType(notificationType.getType());
		notification.setOuterId(outerId);
		notification.setNotifier(comment.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver);
		notification.setOuterTitle(outerTitle);
		notification.setNotifierName(notifierName);
		notificationDao.insert(notification);

	}

	// 通过parentId查找comment的子评论
	public List<commentDTO> listByTargetId(Comment c) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		commentDao = new commentDao(s);
		sqlDao = new SqlDao(s);
		List<Comment> comments = commentDao.listByParentId(c);
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		// 获取评论人
		Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
		List<Long> userIds = new ArrayList<Long>();
		userIds.addAll(commentator);
		// 获取评论人并转换为map
		List<User> users = sqlDao.selectByUsersId(userIds);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
		// 转换comment为commentDTO
		List<commentDTO> commentDTOS = comments.stream().map(comment -> {
			commentDTO commentDTO = new commentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());

		return commentDTOS;
	}

}
