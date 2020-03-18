package li.dto;

import li.model.DialogInfo;
import li.model.User;

public class GetDialogUserDTO {
	private User user;
	private DialogInfo dialogInfo;
	public DialogInfo getDialogInfo() {
		return dialogInfo;
	}
	public void setDialogInfo(DialogInfo dialogInfo) {
		this.dialogInfo = dialogInfo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
