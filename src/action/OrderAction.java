package action;

import java.util.List;

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
import model.CrawlOutputBean;

public class OrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);

			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
			MovieDB mdb = new MovieDB();

			List<CrawlOutputBean> orderbylike = mdb.DBoutput(source);

			for(CrawlOutputBean cbn : orderbylike) {
				int movie_id = cbn.getMovie_id();
				int likeCount = mdb.likeCount(movie_id, source);
				mdb.updateLike(likeCount, movie_id, source);
			}

			orderbylike = mdb.orderbylike(source);
			String loginId = loginForm.getId();
			loginForm.setId(loginId);

			session.setAttribute("orderbylike", orderbylike);
			session.setAttribute("loginFrom", loginForm);

			return mapping.findForward("orderbylike");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}
}

