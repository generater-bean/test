package li.dto;

import li.model.DialogInfo;
import li.model.Friend;
import li.model.User;

public class GetDialogUserDTO {
	private User user;
	private DialogInfo dialogInfo;
	private Friend friend;
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
	public Friend getFriend() {
		return friend;
	}
	public void setFriend(Friend friend) {
		this.friend = friend;
	}

}
