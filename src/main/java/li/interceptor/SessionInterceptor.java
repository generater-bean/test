package li.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import li.mapper.SqlDao;
import li.mapper.SqlSessionmapper;
import li.model.User;
import li.service.NotificationService;
@Service
public class SessionInterceptor implements HandlerInterceptor {
	@Autowired
	private SqlDao sqlDao  ;
	@Autowired
	private NotificationService notificationService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			//获取数据库信息
				SqlSession s =SqlSessionmapper.getSqlSession();
				sqlDao =new SqlDao(s);
				User user=null;
		       Cookie[] cookies=request.getCookies();
		       
		       if(cookies!=null&&cookies.length!=0) {
		    	for(Cookie cookie :cookies) {
		    		if(cookie.getName().equals("token")) {
		    			String token=cookie.getValue();
		    			user=sqlDao.selectByToken(token);
		    			if(user!=null) {
		    				request.getSession().setAttribute("user",user);
		    				long unreadCount = notificationService.unreadCount(user.getId());
		    				request.getSession().setAttribute("unreadCount",unreadCount);
		    				}
		    					break;
		    			}
		    		}
		    	}
		
		
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
