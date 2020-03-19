package li.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import li.dto.GetDialogDTO;
import li.dto.GetDialogUserDTO;
import li.dto.PaginationDTO;
import li.dto.ResultDTO;
import li.exception.CustomizeErrorCode;
import li.model.DialogInfo;
import li.model.Friend;
import li.model.User;
import li.service.DialogService;

@Controller
public class DialogController {

	@Autowired
	private DialogService dialogService;

	@GetMapping("/dialog/{action}")
	public String dialog(HttpServletRequest request, @PathVariable(name = "action") String action, Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "15") Integer size) {

		// 获取登录的user信息

		User user = (User) request.getSession().getAttribute("user");

		// 如果未登录 则返回首页
		if (user == null) {
			return "redirect:/";
		}
		// 检查网页传输的信息
		switch (action) {
		case "all":
			model.addAttribute("section", "all");
			model.addAttribute("sectionName", "用户推荐");
			// 获取用户的信息和问题
			PaginationDTO<GetDialogUserDTO> paginationsDto1 = dialogService.selectUserInfo(user,page, size);
			model.addAttribute("paginations", paginationsDto1);
			break;

		case "myviewer":
			model.addAttribute("section", "myviewer");
			model.addAttribute("sectionName", "关注的用户");
			PaginationDTO<GetDialogUserDTO> paginationsDto2 = dialogService.selectFriendInfo(user, page, size);
			model.addAttribute("paginations", paginationsDto2);
			// 获取用户的信息和问题

			break;

		case "pDialogInfo":
			model.addAttribute("section", "pdialog");
			model.addAttribute("sectionName", "私信 ");
			PaginationDTO<GetDialogUserDTO> paginationsDto3 = dialogService.selectByStatus(user, page, size);
			model.addAttribute("paginations", paginationsDto3);
			// 获取用户的信息和问题

			break;

		default:
			break;
		}
		return "dialog";

	}

	@ResponseBody
	@RequestMapping(value = "/getdialog", method = RequestMethod.POST)
	public Object getDialog(@RequestBody GetDialogDTO getDialogDTO) {

		DialogInfo dialog = new DialogInfo();
		dialog.setDialogContent(getDialogDTO.getDialogContent());
		dialog.setDialogId(getDialogDTO.getDialogId());
		dialog.setDialogName(getDialogDTO.getDialogName());
		dialog.setOuterId(getDialogDTO.getOuterId());
		dialog.setOuterName(getDialogDTO.getOuterName());
		dialog.setStatus(0);
		try {
			dialogService.addDialog(dialog);
			return ResultDTO.okOf();
		} catch (Exception e) {
			return ResultDTO.errorOf(CustomizeErrorCode.TXET_NO_FIND);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/addfriend", method = RequestMethod.POST)
	public Object addfriend(@RequestBody Friend friend) {

		if (friend.getStatus() == 1) {
			try {
				dialogService.addFriend(friend);
				return ResultDTO.okOf();
			} catch (Exception e) {
				return ResultDTO.errorOf(CustomizeErrorCode.READ_NOTIFICATION);
			}

		}else {
			
				dialogService.resetFriend(friend);
				return ResultDTO.okOf();
			

			
		}

	}

}
