package li.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import li.model.User;
import li.service.UserService;
@Controller
public class PersonInfoController {
	@Autowired
	private UserService userService;

	
	@GetMapping("/userInfo")
	public String UserInfo01(HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("user");
		 if(user==null) {
		    	return "redirect:/";
		    		}
		 request.getSession().setAttribute("user", user);   //传user给网页
		return "userInfo";
	}
	
	@PostMapping("/updateInfo")
	public String UserInfo(
			@RequestParam(name= "nickname" )String nickname,
			@RequestParam(name= "password" )String password,
			@RequestParam(name= "email" )String email,
			@RequestParam(name="address"  )String address,
			@RequestParam(name="sex" )int sex,
			HttpServletRequest request) {
		
		User user=(User)request.getSession().getAttribute("user");
		
		if(!address.equals("")) {
			user.setAddress(address);
		}
		if(!String.valueOf(sex).equals("")) {
			user.setSex(sex);
		}
		
		if(!nickname.equals("")) {
			user.setNickname(nickname);
		}
		if(!email.equals("")) {
			user.setEmail(email);
		}
		if(!password.equals("")) {
			user.setPassword(password);
		}
		
		
		
		userService.updateUserInfo(user);
		return "/userInfo";
	}
}
