package li.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import li.dto.GetDialogUserDTO;
import li.dto.PaginationDTO;
import li.enums.NotificationStatusEnum;
import li.mapper.SqlSessionmapper;
import li.mapper.dialogDao;
import li.model.DialogInfo;
import li.model.Friend;
import li.model.Profile;
import li.model.User;

@Service
public class DialogService {
	@Autowired
	private dialogDao dialogDao;

	public void addDialog(DialogInfo dialog) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		dialog.setGmtCreate(System.currentTimeMillis());
		dialogDao.insertDialog(dialog);
	}

	/**
	 * 推荐用户
	 * 
	 * @param user2
	 * @param page
	 * @param size
	 * @return
	 */
	public PaginationDTO<GetDialogUserDTO> selectUserInfo(User user2, Integer page, Integer size) {

		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		// 判断是否大于最大页和小于最小页
		java.util.List<GetDialogUserDTO> getDialogUserDTOs = new ArrayList<>();
		PaginationDTO<GetDialogUserDTO> paginationDTO = new PaginationDTO<GetDialogUserDTO>();

		int totalCount = dialogDao.acountUser();
		paginationDTO.setpagination(totalCount, page, size);
		// size*(page-1);
		Profile profile = new Profile();
		Integer offset = size * (paginationDTO.getPage() - 1);
		profile.setOffset(offset);
		profile.setSize(size);
		java.util.List<User> users = dialogDao.selectUser(profile);

		if (users != null && users.size() >= 0) {

			for (User user : users) {
				GetDialogUserDTO dialogUserDTO = new GetDialogUserDTO();
				//
				dialogUserDTO.setUser(user);
				//
				Friend friend = new Friend();
				friend.setFriendId(user.getId());
				friend.setUserId(user2.getId());
				Friend fs = dialogDao.selectByUserId(friend);
				if (fs == null) {
					Friend Status= new Friend();
					Status.setStatus(1);
					dialogUserDTO.setFriend(Status);
				}else {
					Friend Status= new Friend();
					Status.setStatus(2);
					dialogUserDTO.setFriend(Status);
				}
				//

				getDialogUserDTOs.add(dialogUserDTO);
			}

			paginationDTO.setData(getDialogUserDTOs);

		}

		return paginationDTO;

	}

	public PaginationDTO<GetDialogUserDTO> selectByStatus(User user, Integer page, Integer size) {

		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		// 判断是否大于最大页和小于最小页
		PaginationDTO<GetDialogUserDTO> paginationDTO = new PaginationDTO<GetDialogUserDTO>();

		int totalCount = dialogDao.acountStatus(); //

		paginationDTO.setpagination(totalCount, page, size);
		// size*(page-1);
		Profile profile = new Profile();
		Integer offset = size * (paginationDTO.getPage() - 1);
		profile.setOffset(offset);
		profile.setSize(size);
		profile.setUserId(user.getId());
		java.util.List<GetDialogUserDTO> getDialogUserDTOs = new ArrayList<>();
		java.util.List<DialogInfo> dialogs = dialogDao.selectByStatus(profile);

		if (dialogs != null && dialogs.size() >= 0) {

			for (DialogInfo dialog : dialogs) {
				GetDialogUserDTO dialogUserDTO = new GetDialogUserDTO();
				dialogUserDTO.setDialogInfo(dialog);
				User duser = dialogDao.selectById(dialog.getOuterId());
				dialogUserDTO.setUser(duser);
				getDialogUserDTOs.add(dialogUserDTO);
			}

			paginationDTO.setData(getDialogUserDTOs);

		}
		// 添加私信已读
		DialogInfo dia = new DialogInfo();
		dia.setDialogId(user.getId());
		dia.setStatus(NotificationStatusEnum.READ.getStatus());
		dialogDao.updateStatus(dia);

		return paginationDTO;

	}

	/**
	 * 未知私信
	 * 
	 * @param id
	 * @return
	 */

	public long unreadCount(long id) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		DialogInfo dialogInfo = new DialogInfo();
		dialogInfo.setDialogId(id);
		dialogInfo.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		return dialogDao.accoutByIdAndStatus(dialogInfo);
	}

	/**
	 * 添加关注
	 * 
	 * @param id
	 * @return
	 */
	public void addFriend(Friend friend) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		dialogDao.insertFriend(friend);
	}

	/**
	 * 取消关注
	 * 
	 * @param id
	 * @return
	 */
	public void resetFriend(Friend friend) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		dialogDao.resetFriend(friend);
	}

	public PaginationDTO<GetDialogUserDTO> selectFriendInfo(User user, Integer page, Integer size) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		dialogDao = new dialogDao(s);
		// 判断是否大于最大页和小于最小页
		PaginationDTO<GetDialogUserDTO> paginationDTO = new PaginationDTO<GetDialogUserDTO>();

		int totalCount = dialogDao.acountFriend(user.getId()); //

		paginationDTO.setpagination(totalCount, page, size);
		// size*(page-1);
		Profile profile = new Profile();

		Integer offset = size * (paginationDTO.getPage() - 1);
		if (offset < 0) {
			offset = 0;
		}
		profile.setOffset(offset);
		profile.setSize(size);
		profile.setUserId(user.getId());
		java.util.List<GetDialogUserDTO> getDialogUserDTOs = new ArrayList<>();
		java.util.List<Friend> friends = dialogDao.selectByUserId(profile);

		if (friends != null && friends.size() >= 0) {

			for (Friend friend : friends) {
				GetDialogUserDTO dialogUserDTO = new GetDialogUserDTO();
				dialogUserDTO.setFriend(friend);
				User duser = dialogDao.selectById(friend.getFriendId());
				dialogUserDTO.setUser(duser);
				getDialogUserDTOs.add(dialogUserDTO);
			}

			paginationDTO.setData(getDialogUserDTOs);

		}
		return paginationDTO;
	}

}
