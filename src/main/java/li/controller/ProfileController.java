package li.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import li.dto.NotificationDTO;
import li.dto.PaginationDTO;
import li.dto.QuestionDTO;
import li.model.User;
import li.service.NotificationService;
import li.service.QuestionService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class ProfileController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/profile/{action}")
	public String profile(HttpServletRequest request, @PathVariable(name = "action") String action, Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
		// 获取登录的user信息

		User user = (User) request.getSession().getAttribute("user");

		// 如果未登录 则返回首页
		if (user == null) {
			return "redirect:/";
		}
		// 检查网页传输的信息
		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的提问");
			// 获取用户的信息和问题
			PaginationDTO<QuestionDTO> paginationsDto = questionService.getProfile(user.getId(), page, size);
			model.addAttribute("paginations", paginationsDto);
		} else if ("replies".equals(action)) {
			PaginationDTO<NotificationDTO> paginationDTO = notificationService.list(user.getId(), page, size);
			model.addAttribute("section", "replies");
			model.addAttribute("paginations", paginationDTO);
			model.addAttribute("sectionName", "最新回复");
		}

		return "profile";
	}
}
