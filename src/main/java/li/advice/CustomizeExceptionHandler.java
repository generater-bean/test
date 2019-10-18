package li.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import li.dto.ResultDTO;
import li.exception.CustomizeErrorCode;
import li.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {
	@ExceptionHandler(Exception.class)
	
	ModelAndView handle(HttpServletRequest request,
					HttpServletResponse response,
						Throwable e,Model model
							) {
		String contentType=request.getContentType()	;
		if("application/json".equals(contentType)) {
			//返回JSON
			ResultDTO resultDTO=null;
			if(e instanceof CustomizeException) {
				resultDTO= ResultDTO.errorOf((CustomizeException) e);
			}else {
				resultDTO= ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
			}
			
			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer=response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			} catch (IOException ioe) {
				
			}
			return null;
		
		}
		else {
			//错误页面跳转
		
		
			if(e instanceof CustomizeException) {
				model.addAttribute("message",e.getMessage());
			}else {
			model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
			}
			return new ModelAndView("error");
		}
		
	}

	
}
