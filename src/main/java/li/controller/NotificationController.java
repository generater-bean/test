package li.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import li.dto.NotificationDTO;
import li.enums.NotificationTypeEnum;
import li.model.User;
import li.service.NotificationService;

@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	@GetMapping("/notification/{id}")
	public String profile(HttpServletRequest request, @PathVariable(name = "id") long id) {
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return "redirect:/";
		}
		NotificationDTO notificationDTO=notificationService.read(id,user);
		if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()) {
			return "redirect:/question/"+notificationDTO.getOuterId();
		}else {
			return "redirect:/";
		}
		
	}
}
