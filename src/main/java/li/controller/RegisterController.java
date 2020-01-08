package li.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import li.model.User;
import li.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userservice;
	@GetMapping("/register")
	
	public String register(Model model){
	
		return "register";
	}
	
	@PostMapping("/register")
	public String addReister(@RequestParam("localUserName")String localUserName,
			@RequestParam("localUserPassword")String localUserPassword
			,Model model) {
		model.addAttribute("localUserName",localUserName);
		
		model.addAttribute("localUserPassword",localUserPassword);
		if(localUserName==null||localUserName=="") {
			model.addAttribute("error","用户名不能为空");
			return "register";
		}
		if(localUserPassword==null||localUserPassword=="") {
			model.addAttribute("error","密码不能为空");
			return "register";
		}
		
		
		User user =new User();
		String  token=UUID.randomUUID().toString();
		user.setToken(token);
		user.setName(localUserName);
		user.setAccountId(localUserPassword);
		user.setAvatarUrl("http://pics.sc.chinaz.com/Files/pic/icons128/7570/e10.png");
		if(userservice.addRegister(user)) {
			model.addAttribute("localUserName",localUserName);
			return "redirect:/";
		}else {
			model.addAttribute("error","该用户应被注册！");
			return "register";
		}
		
		
	}
	
	
}
