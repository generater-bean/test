package li.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import li.dto.NotificationDTO;
import li.dto.PaginationDTO;
import li.enums.NotificationStatusEnum;
import li.enums.NotificationTypeEnum;
import li.exception.CustomizeErrorCode;
import li.exception.CustomizeException;
import li.mapper.SqlSessionmapper;
import li.mapper.notificationDao;
import li.model.Notification;
import li.model.Profile;
import li.model.User;

@Service
public class NotificationService {
	@Autowired
	private notificationDao notificationDao;

	public PaginationDTO<NotificationDTO> list(long userId, Integer page, Integer size) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		notificationDao = new notificationDao(s);
		// 判断是否大于最大页和小于最小页
		PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
		int totalCount = notificationDao.acountByReceiver(userId); // 统计
		paginationDTO.setpagination(totalCount, page, size);

		// size*(page-1);计算页数
		Profile profile = new Profile();
		Integer offset = size * (paginationDTO.getPage() - 1);
		profile.setOffset(offset);
		profile.setSize(size);
		profile.setUserId(userId);
		List<Notification> notifications = notificationDao.selectByReceiver(profile);
		if (notifications.size() == 0) {
			return paginationDTO;
		}
		List<NotificationDTO> notificationDTOs = new ArrayList<>();
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = new NotificationDTO();			
			BeanUtils.copyProperties(notification, notificationDTO);			
			notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationDTOs.add(notificationDTO);
		}

		paginationDTO.setData(notificationDTOs);
		return paginationDTO;
	}

	public long unreadCount(long receiverId) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		notificationDao = new notificationDao(s);
		Notification notification=new Notification();
		notification.setReceiver(receiverId);
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		return notificationDao.acountByReceiverStatus(notification);
	}

	public NotificationDTO read(long id, User user) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		notificationDao = new notificationDao(s);
		Notification notification=notificationDao.selectById(id);
		if(notification==null) {
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if(!Objects.equals(notification.getReceiver(), user.getId())) {
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION);
		}
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		notificationDao.updateById(notification);
		NotificationDTO notificationDTO = new NotificationDTO();
		BeanUtils.copyProperties(notification, notificationDTO);
		notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
		return notificationDTO;
	}

}
