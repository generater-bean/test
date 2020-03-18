package li.model;

public class DialogInfo {
	private long  id;
	private long  outerId;
	private String  outerName;
	private long  dialogId;
	private String  dialogName;
	private String  dialogContent;
	private int  status;
	private int type;
	private long  gmtCreate;
	
	public long getDialogId() {
		return dialogId;
	}
	public void setDialogId(long dialogId) {
		this.dialogId = dialogId;
	}
	public String getDialogName() {
		return dialogName;
	}
	public void setDialogName(String dialogName) {
		this.dialogName = dialogName;
	}
	public String getDialogContent() {
		return dialogContent;
	}
	public void setDialogContent(String dialogContent) {
		this.dialogContent = dialogContent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		if(status==0||status==1) {
			this.status = status;
		}else {
			return;
		}
		
	}
	public long getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getOuterName() {
		return outerName;
	}
	public void setOuterName(String outerName) {
		this.outerName = outerName;
	}
	public long getOuterId() {
		return outerId;
	}
	public void setOuterId(long outerId) {
		this.outerId = outerId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
