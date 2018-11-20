package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import action.form.LoginForm;
import action.form.ReviewForm;
import dao.MovieDB;
import model.ReviewBean;

public class ReviewAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (this.isCancelled(request)) {
			return mapping.findForward("back");
		}
		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);
			ReviewForm rfm = (ReviewForm) form;
			ReviewBean rbn = (ReviewBean) session.getAttribute("rbn");
			MovieDB mdb = new MovieDB();

			if (rfm.getReview().equals("") && rfm.getLike() == 0) {
				ActionMessages errors = new ActionMessages();
				errors.add("error.review.required", new ActionMessage("error.review.required"));
				saveErrors(request, errors);
				return mapping.findForward("error");
			}

			String title = rbn.getMovie_title();
			int movie_id = mdb.Id(title, source);

			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

			String user_id = loginForm.getId();
			int login_id = mdb.loginId(user_id, source);

			rfm.setLogin_id(login_id);
			rfm.setMovie_id(movie_id);

			mdb.reviewInput(rfm, source);
			return mapping.findForward("reviewEnd");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}

}
