package li.exception;

public enum  CustomizeErrorCode  implements ICustomizeErrorCode {
		QUESTION_NOT_FOUND(2001,"你找的问题不在了,要不要换个试试？"),
		TARGET_PARAM_NOT_FOUND(2002," 未选中任何问题或评论进行回复"),
		NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
		SYSTEM_ERROR(2004,"服务 冒烟了！！！，请重新尝试"),
		TYPE_PARAM_NOT_FOUND(2005," 评论类型错误或不存在"), 
		COMMENT_NOT_FIND(2006," 回复的评论不存在了，要不要换个试试？"),
		TXET_NO_FIND(2007," 文本为空，请评论"),
		NO_EXICT_ACOUNT(3000,"账号已存在！"),
		READ_NOTIFICATION(2008,"非法操作，这是别人的信息！"),
		NOTIFICATION_NOT_FOUND(2009,"消息走丢了"),
		;
		@Override
		public String getMessage()	{
			return message;
		}
		@Override
		public Integer getCode() {
			
			return code;
		}
		private Integer code;
		private  String message;
		
		 CustomizeErrorCode(Integer code,String message) {
		
			this.message=message;
			this.code=code;
		}

		
}
