package li.controller;
	

import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import li.dto.AccessTokenDTO;
import li.dto.GithubUser;
import li.dto.QQInfoDTO;
import li.dto.QQUserDTO;
import li.model.User;
import li.provider.GithubProvider;
import li.provider.QQProvider;
import li.service.UserService;
/**
	 * 
	 * @author Administrator
	 *
	 */
@Controller
public class AuthorizeController {
	@Autowired
	private QQProvider qqProvider;
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

	
	@Value("${qq.client.id}")
	private String QQclientId;
	@Value("${qq.client.secret}")
	private String QQclientSecret;
	@Value("${qq.redirect.url}")
	private String QQredirecturl;
	
	/**
	 * qq登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/connect")
    public String qqcallback(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String uuid = (String) session.getAttribute("state");
 
        if(uuid != null){
            if(!uuid.equals(state)){
                System.out.println("QQ,state错误");
            }
        }
        QQInfoDTO qqInfoDTO=new QQInfoDTO();
        qqInfoDTO.setClientId(QQclientId);
        qqInfoDTO.setCode(code);
        qqInfoDTO.setClientSecret(QQclientSecret);
        qqInfoDTO.setRedirectUri(QQredirecturl);
        qqInfoDTO.setAccessToken(qqProvider.getAccessToken(qqInfoDTO));//Step2：通过Authorization Code获取Access Token
        qqInfoDTO.setOpenId(qqProvider.getOpenId(qqInfoDTO)); //Step3: 获取回调后的 openid 值
        QQUserDTO qqUserDTO=qqProvider.getUser(qqInfoDTO); //Step4：获取QQ用户信息

        if(qqUserDTO!=null ) {
			User user=new User();
			//String  token=UUID.randomUUID().toString();
			user.setToken(qqInfoDTO.getAccessToken());
			user.setName(qqUserDTO.getNickname());
			user.setAccountId(String.valueOf(qqUserDTO.getOpenid()));
			user.setAvatarUrl(qqUserDTO.getFigureurl_qq_2());
			userservice.createOrUpdate(user);
			response.addCookie(new Cookie("token", qqInfoDTO.getAccessToken()));
			//登陆成功，写cookie和session
			request.getSession().setAttribute("user", user);   //传user给网页
			return "redirect:/";
		}else {
			//登录失败
			return "redirect:/";
		}
    }
	
	
	
	
	
	/**
	 * github登录
	 * @param code
	 * @param state
	 * @param request
	 * @param response
	 * @return
	 */
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
			request.getSession().setAttribute("user", user);   //传user给网页
			return "redirect:/";
		}else {
			//登录失败
			return "redirect:/";
		}
		
	}
	/**
	 * 本地用户登录
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/localLogin")
	public String  localLogin(HttpServletRequest request,
			@RequestParam(name="localUser")String localUser,
			@RequestParam(name="localUserpwd")String localUserpwd,
			HttpServletResponse response,Model model ) {
		
		User user=new User();
		//String  token=UUID.randomUUID().toString();
		//user.setToken(token);
		user.setName(localUser);
		user.setAccountId(String.valueOf(localUserpwd));
		//user.setAvatarUrl(githubUser.getAvatar_url());
		User dbuser =userservice.localUserLogin(user);
		if(dbuser!=null) {
		response.addCookie(new Cookie("token", dbuser.getToken()));
		//登陆成功，写cookie和session
		request.getSession().setAttribute("user", user);   //传user给网页
		return "redirect:/";
		}else {
			
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
