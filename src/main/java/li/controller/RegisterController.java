package li.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import li.dto.ResultDTO;
import li.dto.registerDTO;
import li.exception.CustomizeErrorCode;
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
	
	@ResponseBody
	@RequestMapping(value = "/adduser",method =RequestMethod.POST)
	public Object addUser(
			@RequestBody registerDTO registerDTO) {

		User user =new User();
		String  token=UUID.randomUUID().toString();
		user.setToken(token);
		user.setNickname(registerDTO.getUsername());
		user.setRole(registerDTO.getRole());
		user.setPassword(registerDTO.getUserpassword());
		user.setAddress(registerDTO.getAddress());
		user.setEmail(registerDTO.getEmail());
		user.setSex(registerDTO.getSex());
		user.setAvatarUrl("http://pics.sc.chinaz.com/Files/pic/icons128/7570/e10.png");
		
		if(userservice.localAddRegister(user)==true) {
			
			return ResultDTO.okOf();
		}else {
			
			return ResultDTO.errorOf(CustomizeErrorCode.NO_EXICT_ACOUNT);
		}
		
	}
	
	
}
