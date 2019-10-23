package li.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import li.dto.QQInfoDTO;
import li.dto.QQUserDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class QQProvider {
	/**
     * getUserInfo
     * @param accessToken
     * @param openid
     * @return
     */
    public QQUserDTO getUser(QQInfoDTO qqInfoDTO)	{
		OkHttpClient client = new OkHttpClient();
		  Request request = new Request.Builder()
		      .url("https://graph.qq.com/user/get_user_info?access_token="+ qqInfoDTO.getAccessToken()
		    		  +"&oauth_consumer_key="+ qqInfoDTO.getClientId() +"&openid=" + qqInfoDTO.getOpenId()
		    		  )
		      .build();
		  try {
			Response response = client.newCall(request).execute();
			String string  =response.body().string();
			QQUserDTO qqbUser=JSON.parseObject(string,QQUserDTO.class);
			qqbUser.setOpenid(qqInfoDTO.getOpenId());
			return qqbUser;
			
		} catch (IOException e) {
			
		}
		return null;
	}
    /*
     * get AccessToken
     */
    public String getAccessToken(QQInfoDTO qqInfoDTO ) {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
		      .url("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code"+
		                "&client_id=" + qqInfoDTO.getClientId()+
		                "&client_secret=" + qqInfoDTO.getClientSecret() +
		                "&code=" + qqInfoDTO.getCode() +
		                "&redirect_uri=" +qqInfoDTO.getRedirectUri())
		      .build();
		  try (Response response = client.newCall(request).execute()) {
			  String string =response.body().string();
			  String	token=string.split("&")[0].split("=")[1];
			
		    return token;
		  }catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
		}
		  return null;	  
    }
    /**
     * get openId
     * @param url
     * @return
     */
    public String getOpenId(QQInfoDTO qqInfoDTO ) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
		      .url("https://graph.qq.com/oauth2.0/me?access_token=" + qqInfoDTO.getAccessToken())
		      .build();
		  try (Response response = client.newCall(request).execute()) {
			  String string =response.body().string();
			  String	OpenId=string.split(",")[1].split(":")[1].split("\"")[1];
			
		    return OpenId;
		  }catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
		}
		  return null;	  
    }
}
