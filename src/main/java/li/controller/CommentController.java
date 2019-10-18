package li.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import li.dto.ResultDTO;
import li.dto.commentCreateDTO;
import li.dto.commentDTO;
import li.enums.CommentTypeEnum;
import li.exception.CustomizeErrorCode;
import li.model.Comment;
import li.model.User;
import li.service.CommentService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class CommentController {
	
	@Autowired 
	private CommentService commentservice;
	@ResponseBody
	@RequestMapping(value = "/comment",method =RequestMethod.POST)
	public Object post(@RequestBody commentCreateDTO commentCreateDTO,
						HttpServletRequest request
								) {
		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		if(commentCreateDTO==null||org.apache.commons.lang3.StringUtils.isBlank(commentCreateDTO.getContent())) {
			
			return ResultDTO.errorOf(CustomizeErrorCode.TXET_NO_FIND);
		}	
			Comment comment=new Comment();
			comment.setParentId(commentCreateDTO.getParentId());
			comment.setContent(commentCreateDTO.getContent());
			comment.setType(commentCreateDTO.getType());
			comment.setGmtCreate(System.currentTimeMillis());
			comment.setGmtModified(comment.getGmtCreate());
			comment.setCommentator(user.getId());
			//comment.setCommentator(1);
			
			commentservice.insertComm(comment,user);
		
		
		return ResultDTO.okOf();
		
		
	}
	@ResponseBody
	@RequestMapping(value = "/comment/{id}",method =RequestMethod.GET)
	public ResultDTO<List<commentDTO>> comments(@PathVariable(name="id")long id) {
		
		Comment c=new Comment();
		 c.setParentId(id);
		 c.setType(CommentTypeEnum.COMMENT.getType());
		List<commentDTO> commentDTOS = commentservice.listByTargetId(c);
		return ResultDTO.okOf(commentDTOS);
	}
}
