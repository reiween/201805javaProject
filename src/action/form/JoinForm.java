package action.form;

import org.apache.struts.action.ActionForm;

public class JoinForm extends ActionForm {

	private String id;
	private String pw;
	private String pw_profit;

	public JoinForm() {
		id = "";
		pw = "";
		pw_profit = "";
	}

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
	public String getPw_profit() {
		return pw_profit;
	}
	public void setPw_profit(String pw_profit) {
		this.pw_profit = pw_profit;
	}

}
