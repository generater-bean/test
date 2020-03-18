package li.dto;

public class GetDialogDTO {
	private long  outerId;
	private String  outerName;
	private long  dialogId;
	private String  dialogName;
	private String  dialogContent;
	
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
}
