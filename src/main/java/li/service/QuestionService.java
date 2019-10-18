package li.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import li.dto.PaginationDTO;
import li.dto.QuestionDTO;
import li.dto.QuestionQueryDTO;
import li.exception.CustomizeErrorCode;
import li.exception.CustomizeException;
import li.mapper.SqlDao;
import li.mapper.SqlSessionmapper;
import li.model.Profile;
import li.model.Question;
import li.model.User;

/**
 * 获取question表和user表的字段值
 */
@Service

public class QuestionService {
	@Autowired
	private SqlDao sqlDao;

	// 获取数据库的所有问题
	public PaginationDTO<QuestionDTO> list(String search,Integer page, Integer size) {
		if (StringUtils.isNotBlank(search)) {
			String[] tags = StringUtils.split(search, " ");
			search = Arrays.stream(tags).collect(Collectors.joining("|"));
			
		} else if(search=="") {
			search=null;
		}
		
		
		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);

		// 判断是否大于最大页和小于最小页
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		QuestionQueryDTO questionQueryDTO=new QuestionQueryDTO();
		questionQueryDTO.setSearch(search);
		int totalCount = sqlDao.acountBySearch(questionQueryDTO); // 统计
		if(totalCount==0) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		paginationDTO.setpagination(totalCount, page, size);

		// size*(page-1);计算页数
		Integer offset = size * (paginationDTO.getPage() - 1);
		questionQueryDTO.setPage(offset);
		questionQueryDTO.setSize(size);
		java.util.List<QuestionDTO> questionDTOlist = new ArrayList<>();
		java.util.List<Question> questions = sqlDao.selectBySearch(questionQueryDTO);

		if (questions != null && questions.size() >= 0) {
			for (Question question : questions) {
				QuestionDTO questionDTO = new QuestionDTO();
				BeanUtils.copyProperties(question, questionDTO);// 将question的值复制到questionDTO
				questionDTOlist.add(questionDTO);
				User user = sqlDao.selectById(question.getCreator());
				// if(user!=null)
				// questionDTO.setAvatarUrl(user.getAvatarUrl());
				questionDTO.setUser(user);
			}
			paginationDTO.setData(questionDTOlist);
		}

		return paginationDTO;
	}

	// 获取个人发布的问题
	public PaginationDTO<QuestionDTO> getProfile(long userId, Integer page, Integer size) {

		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);

		// 判断是否大于最大页和小于最小页
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		int totalCount = sqlDao.acountById(userId); //
		paginationDTO.setpagination(totalCount, page, size);

		// size*(page-1);
		Profile profile = new Profile();
		Integer offset = size * (paginationDTO.getPage() - 1);
		profile.setOffset(offset);
		profile.setSize(size);
		profile.setUserId(userId);

		java.util.List<QuestionDTO> questionDTOlist = new ArrayList<>();
		java.util.List<Question> questions = sqlDao.selectProfile(profile); // 获取个人发布信息

		if (questions != null && questions.size() >= 0) {
			for (Question question : questions) {
				QuestionDTO questionDTO = new QuestionDTO();
				BeanUtils.copyProperties(question, questionDTO);// 将question的值复制到questionDTO
				questionDTOlist.add(questionDTO);
				User user = sqlDao.selectById(question.getCreator());
				if (user != null)
					questionDTO.setUser(user);

			}
			paginationDTO.setData(questionDTOlist);
		}

		return paginationDTO;
	}

	// 通过id查询问题
	public QuestionDTO getById(long id) {
		QuestionDTO questionDTO = new QuestionDTO();

		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);
		Question question = sqlDao.getById(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		BeanUtils.copyProperties(question, questionDTO);
		User user = sqlDao.selectById(question.getCreator());
		questionDTO.setUser(user);
		return questionDTO;
	}

	// 发布问题或者编辑问题
	public void createOrUpdate(Question question) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);
		if (question.getId() == 0) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setCommentCount(0);
			question.setLikeCount(0);
			question.setViewCount(0);
			sqlDao.addTitle(question);
		} else {
			question.setGmtModified(System.currentTimeMillis());
			int update = sqlDao.questionUpdate(question);
			if (update != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}

	}

	// 增加阅读数
	public void incView(long id) {
		SqlSession s = SqlSessionmapper.getSqlSession();
		sqlDao = new SqlDao(s);

		int update = sqlDao.incViewCount(id);
		if (update != 1) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
	}

	public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
		if (StringUtils.isBlank(queryDTO.getTag())) {
			return new ArrayList<QuestionDTO>();
		}
		String[] tags = StringUtils.split(queryDTO.getTag(), ",");
		String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
		Question question = new Question();
		question.setTag(regexpTag);
		question.setId(queryDTO.getId());
		List<Question> questions = sqlDao.selectRelated(question);
		List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(q, questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());
		return questionDTOs;
	}
}
