package uno;

import java.io.Serializable;

public class MenuData implements Serializable{
	private String button;
	private String user;
	
	public String getButtonName() {
		return button;
	}
	
	public String getUserName() {
		return user;
	}
	
	public void setButtonName(String button) {
		this.button = button;
	}
	
	public void setUserName(String user) {
		this.user = user;
	}
	
	public MenuData(String button, String user) {
		setButtonName(button);
		setUserName(user);
	}
}
