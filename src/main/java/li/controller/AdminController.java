package li.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import li.dto.PaginationDTO;
import li.dto.QuestionDTO;
import li.dto.UserDTO;
import li.model.User;
import li.service.AdminService;

@Controller
public class AdminController {
	@Autowired 
	private AdminService adminService;

	
	@GetMapping("admin/{action}")
	public String admin(HttpServletRequest request, @PathVariable(name = "action") String action, Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
	
		// 获取登录的user信息

				User user = (User) request.getSession().getAttribute("user");

				// 如果未登录 则返回首页
				if (user == null) {
					return "redirect:/";
				}else if(user.getRole()==1){
					model.addAttribute("error", "您不是系统管理员！");
					return "redirect:/";
				}
				// 检查网页传输的信息
				switch (action) {
				case "role":
					model.addAttribute("section", "role");
					model.addAttribute("sectionName", "用户管理");
					// 获取用户的信息和问题
					PaginationDTO<UserDTO> paginationsDto1 =adminService.selectUserInfo(page, size);
					model.addAttribute("paginations", paginationsDto1);
					break;
					
				case "theme":
					model.addAttribute("section", "theme");
					model.addAttribute("sectionName", "主题管理");
					// 获取用户的信息和问题
					PaginationDTO<QuestionDTO> paginationsDto2 =adminService.selectQuestionInfo(page, size);
					model.addAttribute("paginations", paginationsDto2);
					break;

				default:
					break;
				}
				
		return "admin";
	}
	@GetMapping("deleteQuestion/{id}")
	public String delQuestion(@PathVariable(name="id")long id) {
		adminService.deleteQuestion(id);
		return "redirect:../admin/theme";
		
	}
	@GetMapping("LimitQuestion/{id}")
	public String LimitQuestion(@PathVariable(name="id")long id) {
		
		adminService.LimitQuestion(id);
		return "redirect:../admin/theme";
		
	}
	
	@GetMapping("deleteRole/{id}")
	public String delRole(@PathVariable(name="id")long id) {
		adminService.deleteRole(id);
		return "redirect:../admin/role";
		
	}
	
	
}
