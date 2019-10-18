package li.controller;
	

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import li.dto.AccessTokenDTO;
import li.dto.GithubUser;
import li.model.User;
import li.provider.GithubProvider;
import li.service.UserService;
/**
	 * 
	 * @author Administrator
	 *
	 */
@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubprovider;
	@Autowired
	private UserService userservice;
	//调用application.properties的静态值
	
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.url}")
	private String redirecturl;

	
	@GetMapping("/callback")
	public String  callback(@RequestParam(name="code")String code,
							@RequestParam(name="state")String state
							,HttpServletRequest request,
							HttpServletResponse response) {
		AccessTokenDTO accesstokendto= new AccessTokenDTO();
		accesstokendto.setClient_id(clientId);
		accesstokendto.setClient_secret(clientSecret);
		accesstokendto.setCode(code);
		accesstokendto.setRedirect_url(redirecturl);
		accesstokendto.setState(state);
		String accessToken=githubprovider.getAccessToken(accesstokendto);
		GithubUser githubUser=githubprovider.getUser(accessToken);
		if(githubUser!=null ) {
			User user=new User();
			String  token=UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setAvatarUrl(githubUser.getAvatar_url());
			userservice.createOrUpdate(user);
			response.addCookie(new Cookie("token", token));
			//登陆成功，写cookie和session
			request.getSession().setAttribute("user", githubUser);   //传user给网页
			return "redirect:/";
		}else {
			//登录失败
			return "redirect:/";
		}
		
	}
	@GetMapping("/logout")
	public String  logout(	HttpServletRequest request,
							HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		Cookie cookie=new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
	
}
