package li.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import li.dto.GetDialogUserDTO;
import li.dto.PaginationDTO;
import li.enums.NotificationStatusEnum;
import li.mapper.SqlSessionmapper;
import li.mapper.dialogDao;
import li.model.DialogInfo;
import li.model.Profile;
import li.model.User;

@Service
public class DialogService {
	@Autowired 
	private dialogDao dialogDao;
	
	public void addDialog(DialogInfo dialog) {
		// TODO Auto-generated method stub
		SqlSession s =SqlSessionmapper.getSqlSession();
		dialogDao =new dialogDao(s);
		dialog.setGmtCreate(System.currentTimeMillis());
		dialogDao.insertDialog(dialog);
	}

	public PaginationDTO<User> selectUserInfo(Integer page, Integer size) {
		
			SqlSession s =SqlSessionmapper.getSqlSession();
			dialogDao =new dialogDao(s);
			// 判断是否大于最大页和小于最小页
			PaginationDTO<User> paginationDTO = new PaginationDTO<User>();
			
			int totalCount = dialogDao.acountUser();
			paginationDTO.setpagination(totalCount, page, size);
			// size*(page-1);
					Profile profile = new Profile();
					Integer offset = size * (paginationDTO.getPage() - 1);
					profile.setOffset(offset);
					profile.setSize(size);
					java.util.List<User> users =dialogDao.selectUser(profile);			
					paginationDTO.setData(users);
					
					
					
					
					
			return paginationDTO;
			
		
	}

	public PaginationDTO<GetDialogUserDTO> selectByStatus(User user, Integer page, Integer size) {

		SqlSession s =SqlSessionmapper.getSqlSession();
		dialogDao =new dialogDao(s);
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
						java.util.List<DialogInfo> dialogs =dialogDao.selectByStatus(profile);
						
						if (dialogs != null && dialogs.size() >= 0) {
							
							for (DialogInfo dialog : dialogs) {
								GetDialogUserDTO dialogUserDTO = new GetDialogUserDTO();
								dialogUserDTO.setDialogInfo(dialog);
								User duser=dialogDao.selectById(dialog.getOuterId());
								dialogUserDTO.setUser(duser);
								getDialogUserDTOs.add(dialogUserDTO);
							}
							
							paginationDTO.setData(getDialogUserDTOs);
							
						}
						//添加私信已读
						DialogInfo dia=new DialogInfo();
						dia.setDialogId(user.getId());
						dia.setStatus(NotificationStatusEnum.READ.getStatus());
						dialogDao.updateStatus(dia);
						
				return paginationDTO;
				
	}
	/**
	 *  未知私信
	 * @param id
	 * @return
	 */
	
	public long unreadCount(long id) {
		// TODO Auto-generated method stub
		SqlSession s =SqlSessionmapper.getSqlSession();
		dialogDao =new dialogDao(s);
		DialogInfo dialogInfo=new DialogInfo();
		dialogInfo.setDialogId(id);
		dialogInfo.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		return dialogDao.accoutByIdAndStatus(dialogInfo);
	}
}
