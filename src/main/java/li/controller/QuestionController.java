package li.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import li.dto.QuestionDTO;
import li.dto.commentDTO;
import li.enums.CommentTypeEnum;
import li.model.Comment;
import li.service.CommentService;
import li.service.QuestionService;

/**
 * 
 * @author Administrator
 *
 */

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CommentService commentservice;
	@GetMapping("/question/{id}")
	public String question(@PathVariable(name="id")long id,
							Model model) {	
			QuestionDTO  questionDTO=questionService.getById(id);
			List<QuestionDTO> relatedQuestion=questionService.selectRelated(questionDTO);
			//添加回复 
			Comment comment=new Comment();
			comment.setParentId(id);
			comment.setType(CommentTypeEnum.QUESTION.getType());			
			List<commentDTO> comments=commentservice.listByTargetId(comment);
			//累加阅读数
			questionService.incView(id);
			model.addAttribute("question",questionDTO);
			model.addAttribute("comments",comments);
			model.addAttribute("relatedQuestion",relatedQuestion);
		return "question";
	}
}
