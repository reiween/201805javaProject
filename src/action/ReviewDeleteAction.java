package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import action.form.LoginForm;
import dao.MovieDB;

public class ReviewDeleteAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(this.isCancelled(request)) {

			return mapping.findForward("back");
		}
		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);
			int review_id = Integer.parseInt(request.getParameter("review_id"));
			MovieDB mdb = new MovieDB();

			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
			String loginId = loginForm.getId();
			loginForm.setId(loginId);
			mdb.reviewDelete(review_id, source);

			session.setAttribute("loginForm", loginForm);

			return mapping.findForward("deleteEnd");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}

	}

}
