package action.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	private String id;
	private String pw;

//	public LoginForm() {
//		id = "";
//		pw = "";
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

}
