package li.dto;

public class NotificationDTO {
	private long id;
	private long gmtCreate;
	private Integer status;
	private long notifier;
	private String notifierName;
	private String outerTitle;
	private String  typeName;
	private Integer type;
	private long  outerId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getNotifier() {
		return notifier;
	}
	public void setNotifier(long notifier) {
		this.notifier = notifier;
	}
	public String getNotifierName() {
		return notifierName;
	}
	public void setNotifierName(String notifierName) {
		this.notifierName = notifierName;
	}
	public String getOuterTitle() {
		return outerTitle;
	}
	public void setOuterTitle(String outerTitle) {
		this.outerTitle = outerTitle;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public long getOuterId() {
		return outerId;
	}
	public void setOuterId(long outerId) {
		this.outerId = outerId;
	}
	
	
}
