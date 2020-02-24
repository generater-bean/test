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
import li.mapper.adminDao;
import li.model.Profile;
import li.model.Question;
import li.model.User;

@Service
public class AdminService {
	@Autowired
	private adminDao adminDao;
	/** 用户管理
	 * @author 
	 */
	
	public PaginationDTO<UserDTO> selectUserInfo(int page,int size) {
		SqlSession s =SqlSessionmapper.getSqlSession();
		adminDao =new adminDao(s);
		// 判断是否大于最大页和小于最小页
		PaginationDTO<UserDTO> paginationDTO = new PaginationDTO<UserDTO>();
		
		int totalCount = adminDao.acountUser(); //
		
		paginationDTO.setpagination(totalCount, page, size);
		// size*(page-1);
				Profile profile = new Profile();
				Integer offset = size * (paginationDTO.getPage() - 1);
				profile.setOffset(offset);
				profile.setSize(size);
				java.util.List<UserDTO> userDTOlist = new ArrayList<>();
				
				java.util.List<User> users =adminDao.selectUser(profile);
				
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
	/**
	 * 主题管理
	 * @param page
	 * @param size
	 * @return
	 */
	public PaginationDTO<QuestionDTO> selectQuestionInfo(Integer page, Integer size) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		adminDao =new adminDao(s);

		// 判断是否大于最大页和小于最小页
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		int totalCount = adminDao.acount(); //
		paginationDTO.setpagination(totalCount, page, size);

		// size*(page-1);
		Profile profile = new Profile();
		Integer offset = size * (paginationDTO.getPage() - 1);
		profile.setOffset(offset);
		profile.setSize(size);
		

		java.util.List<QuestionDTO> questionDTOlist = new ArrayList<>();
		java.util.List<Question> questions = adminDao.selectAll(profile); // 获取个人发布信息

		if (questions != null && questions.size() >= 0) {
			for (Question question : questions) {
				QuestionDTO questionDTO = new QuestionDTO();
				BeanUtils.copyProperties(question, questionDTO);// 将question的值复制到questionDTO
				questionDTOlist.add(questionDTO);
				User user = adminDao.selectById(question.getCreator());
				if (user != null)
					questionDTO.setUser(user);

			}
			paginationDTO.setData(questionDTOlist);
		}

		return paginationDTO;
	}

	/**
	 * 删除主题
	 */
	public void deleteQuestion(long id) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		adminDao =new adminDao(s);
		adminDao.deleteQuestion(id);
	}
	/**
	 * 删除用户
	 */
	public void deleteRole(long id) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		adminDao =new adminDao(s);
		adminDao.deleteRole(id);
	}
	/**
	 * 封印主题
	 */
	public void LimitQuestion(long id) {
		// TODO Auto-generated method stub
		SqlSession s = SqlSessionmapper.getSqlSession();
		adminDao =new adminDao(s);
		Question question=new Question();
		question.setId(id);
		int likecount=adminDao.selectLikeCount(question.getId());
		if(likecount==0) {
			question.setLikeCount(1);
		}else {
			question.setLikeCount(0);
		}
		adminDao.LimitQuestion(question);
	}
}
