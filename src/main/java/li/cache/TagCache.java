package li.cache;
/**
 * 
 * @author Administrator
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import li.dto.TagDTO;

public class TagCache {
	public static List<TagDTO> get(){
		List<TagDTO> tagDTOS=new ArrayList<TagDTO>();
		TagDTO program=new TagDTO();
		program.setCategoryName("开发语言");
		program.setTags(Arrays.asList("js","php","css","html","html5","java","node","python","c++","golang","c","shell","tpyescript"));
		tagDTOS.add(program);
		
		
		TagDTO framework=new TagDTO();
		framework.setCategoryName("平台框架");
		framework.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","rudy-on-rails","tornado","koa","struts"));
		tagDTOS.add(framework);
		
		
		TagDTO server=new TagDTO();
		server.setCategoryName("服务器");
		server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat","unix","hadoop","window-server"));
		tagDTOS.add(server);
		
		TagDTO db=new TagDTO();
		db.setCategoryName("数据库");
		db.setTags(Arrays.asList("mysql","redia","mongodb","sql","oracle","nosql memcached","sqlserver","postgresql","sqlite"));
		tagDTOS.add(db);
		
		TagDTO tool=new TagDTO();
		tool.setCategoryName("开发工具");
		tool.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode intellij-idea","eclipse","maven","ide"));
		tagDTOS.add(tool);
		
		return tagDTOS;
	}
	
	public static String filterValid(String tags) {
		String[] split=StringUtils.split(tags,",");
		List<TagDTO> tagDTOs=get();
		List<String> tagList=tagDTOs.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
		String invaild=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
		return invaild;
	}
}
