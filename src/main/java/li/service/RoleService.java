package li.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import li.dto.PaginationDTO;
import li.dto.QuestionDTO;
import li.dto.UserDTO;
import li.mapper.SqlDao;
import li.mapper.SqlSessionmapper;
import li.model.Profile;
import li.model.Question;
import li.model.User;

@Service
public class RoleService {
	@Autowired
	private SqlDao sqlDao;
	/**
	 * @author 
	 */
	public PaginationDTO<UserDTO> selectUserInfo(int page,int size) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		sqlDao =new SqlDao(s);
		// 判断是否大于最大页和小于最小页
		PaginationDTO<UserDTO> paginationDTO = new PaginationDTO<UserDTO>();
		
		int totalCount = sqlDao.acountUser(); //
		
		paginationDTO.setpagination(totalCount, page, size);
		// size*(page-1);
				Profile profile = new Profile();
				Integer offset = size * (paginationDTO.getPage() - 1);
				profile.setOffset(offset);
				profile.setSize(size);
				java.util.List<UserDTO> userDTOlist = new ArrayList<>();
				
				java.util.List<User> users =sqlDao.selectUser(profile);
				
				if (users != null && users.size() >= 0) {
					for (User user : users) {
						UserDTO userDTO = new UserDTO();
						BeanUtils.copyProperties(user, userDTO);// 将question的值复制到questionDTO
						userDTOlist.add(userDTO);
					}
						
					paginationDTO.setData(userDTOlist);
				}
		return paginationDTO;
		
	}
}
