package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import action.form.LoginForm;
import dao.MovieDB;


public class LoginAction extends LookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		// Mapオブジェクト生成し、メッセージキーとメソッド名格納
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", "nextPage");
		map.put("join", "joinPage");
		return map;
	}

	public ActionForward nextPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm login = (LoginForm) form;

		MovieDB mdb = new MovieDB();
		DataSource source = getDataSource(request);
		List<LoginForm> loginlist = mdb.Search(login, source);
		String id = request.getParameter("id");
		String passwd = request.getParameter("pw");

		if(id.equals("") || passwd.equals("")) {
			ActionMessages errors = new ActionMessages();
			errors.add("login.required", new ActionMessage("error.login.required"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

		if (loginlist.isEmpty()) {
			ActionMessages errors = new ActionMessages();
			errors.add("user_id.exist", new ActionMessage("error.user_id.exist"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

		for (LoginForm loginForm : loginlist) {
		if (!passwd.equals(loginForm.getPw())) {
				ActionMessages errors = new ActionMessages();
				errors.add("user_id.exist", new ActionMessage("error.user_id.exist"));
				saveErrors(request, errors);
				return mapping.findForward("error");
			}

		}
		login.setId(id);
		session.setAttribute("loginForm", login);
		session.setAttribute("loginList", loginlist);
		return mapping.findForward("login");
	}

	public ActionForward joinPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		if (session != null) {
			session.invalidate();
		}
		return mapping.findForward("join");
	}

}
