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
import li.service.RoleService;

@Controller
public class AdminController {
	@Autowired 
	private RoleService roleService;
	
	@GetMapping("admin/{action}")
	public String admin(HttpServletRequest request, @PathVariable(name = "action") String action, Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
	
		// 获取登录的user信息

//				User user = (User) request.getSession().getAttribute("user");
//
//				// 如果未登录 则返回首页
//				if (user == null) {
//					return "redirect:/";
//				}
				// 检查网页传输的信息
				if ("role".equals(action)) {
					model.addAttribute("section", "role");
					model.addAttribute("sectionName", "用户管理");
					// 获取用户的信息和问题
					PaginationDTO<UserDTO> paginationsDto =roleService.selectUserInfo(page, size);
					model.addAttribute("paginations", paginationsDto);
				} 
		return "admin";
	}
}
