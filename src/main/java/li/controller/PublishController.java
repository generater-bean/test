package li.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import li.cache.TagCache;
import li.dto.QuestionDTO;
import li.model.Question;
import li.model.User;
import li.service.QuestionService;
@Controller
public class PublishController {
	@Autowired
	private QuestionService questionservice;
	
	
	//编辑问题
	@GetMapping("/publish/{id}")
	public String edit( @PathVariable(name="id")long id,
					Model model) {
	
		QuestionDTO question=questionservice.getById(id);
		model.addAttribute("title",question.getTitle());
		model.addAttribute("description",question.getDescription());
		model.addAttribute("tag",question.getTag());
		model.addAttribute("id",question.getId());
		model.addAttribute("tags",TagCache.get());
		return "publish";
	}
	
	
	
	//发布页面
	@GetMapping("publish")
	public String publish (HttpServletRequest request,Model model) {
	//获取登录的user信息
		
		User user=(User)request.getSession().getAttribute("user");
				
	    if(user==null) {
	    	model.addAttribute("error","用户未登录");
	    	return "redirect:/";
	    		}
		  
		model.addAttribute("tags",TagCache.get());
		return "publish";
	}
	
	//发布问题
	@PostMapping("/publish")
	public  String  doPublish(
			@RequestParam("title")String title,
			@RequestParam("description")String description,
			@RequestParam("tag")String tag,
			@RequestParam(name = "id",defaultValue = "0")long id,
			HttpServletRequest request,
			Model  model) {
		
		model.addAttribute("title",title);
		model.addAttribute("description",description);
		model.addAttribute("tag",tag);
		model.addAttribute("tags",TagCache.get());
		//校验输入数值是否为空
		if(title==null||title=="") {
			model.addAttribute("error","标题不能为空");
			return "publish";
			
		}
		if(description==null||description=="") {
			model.addAttribute("error","问题补充不能为空");
			return "publish";
			
		}
		if(tag==null||tag=="") {
			model.addAttribute("error","标签不能为空");
			return "publish";
			
		}
		String invalid=TagCache.filterValid(tag);
		if(StringUtils.isNoneBlank(invalid)) {
			model.addAttribute("error","输入非法标签");
			return "publish";
		}
		//获取登录的user信息
		
		User user=(User)request.getSession().getAttribute("user");
				
	    if(user==null) {
	    	model.addAttribute("error","用户未登录");
	    	return "publish";
	    		}
		  
	    	//填充实体对象
			 Question question=new Question();
			 question.setTitle(title);
			 question.setDescription(description);
			 question.setTag(tag);
			 question.setGmtCreate(System.currentTimeMillis());
			 question.setGmtModified(question.getGmtCreate());
			 question.setId(id);
			 question.setCreator(user.getId());
	    	//添加到数据库
	    	questionservice.createOrUpdate(question);
	    	
		return "redirect:/";
	}
}

