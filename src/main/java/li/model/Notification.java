package li.model;

public class Notification {
	private long id;
	private long notifier;
	private long receiver;
	private long outerId;
	private Integer type;
	private long gmtCreate;
	private Integer status;
	private  String outerTitle;
	private  String notifierName;
	public  long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNotifier() {
		return notifier;
	}
	public void setNotifier(long notifier) {
		this.notifier = notifier;
	}
	public long getReceiver() {
		return receiver;
	}
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	public long getOuterId() {
		return outerId;
	}
	public void setOuterId(long outerId) {
		this.outerId = outerId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(long gmtCreate) {
		this.gmtCreate = gmtCreate;
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
}
