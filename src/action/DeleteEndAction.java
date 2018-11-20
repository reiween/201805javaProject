package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import action.form.LoginForm;

public class DeleteEndAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

			session.setAttribute("loginForm", loginForm);

			return mapping.findForward("index");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}


}
