package li.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import li.dto.PaginationDTO;
import li.service.QuestionService;

@Controller
public class IndexController {
	@Autowired
	private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request ,
    		Model model ,
    		@RequestParam(name="page",defaultValue = "1")Integer page,
    		@RequestParam(name="size",defaultValue = "5")Integer size,
    		@RequestParam(name="search",required = false)String search
    		) {
    	
       PaginationDTO paginations = questionService.list(search,page,size);
       if(paginations!=null) {
       model.addAttribute("paginations", paginations);
       model.addAttribute("search", search);
       }
       
       return "index";
       
        
    }

}