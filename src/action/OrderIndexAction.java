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
import org.apache.struts.actions.LookupDispatchAction;

import action.form.LoginForm;
import dao.MovieDB;
import model.ReviewBean;

public class OrderIndexAction extends LookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		// Mapオブジェクト生成し、メッセージキーとメソッド名格納
		Map<String, String> map = new HashMap<String, String>();
		map.put("choice", "nextPage");
		map.put("logout", "logoutPage");
		map.put("orderbylike", "orderbylikePage");
		map.put("goindex", "indexPage");
		return map;
	}

	public ActionForward nextPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);
			String title = request.getParameter("title");
			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
			MovieDB mdb = new MovieDB();
			// if(this.isCancelled(request)) {
			// session.removeAttribute("LoginForm");
			// return mapping.findForward("back");
			// }

			String loginId = loginForm.getId();
			loginForm.setId(loginId);
			int movie_id = mdb.Id(title, source);
			List<ReviewBean> reviewList = mdb.reviewSearch(movie_id, source);
			List<ReviewBean> infoList = mdb.infoSearch(movie_id, source);
			int likeCount = mdb.likeCount(movie_id, source);

			for (ReviewBean rbn : reviewList) {
				int login_id = rbn.getLogin_id();
				String user_id = mdb.userId(login_id, source);
				rbn.setUser_id(user_id);

			}

			session.setAttribute("reviewList", reviewList);
			session.setAttribute("infoList", infoList);
			session.setAttribute("likeCount", likeCount);
			session.setAttribute("loginForm", loginForm);

			ReviewBean rbn = new ReviewBean();
			rbn.setLike(likeCount);
			rbn.setMovie_title(title);
			session.setAttribute("rbn", rbn);
			return mapping.findForward("information");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}

	public ActionForward logoutPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("LoginForm");
		return mapping.findForward("back");
	}

	public ActionForward indexPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

		session.setAttribute("loginForm", loginForm);

		return mapping.findForward("goindex");

	}
	public ActionForward orderbylikePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

		session.setAttribute("loginForm", loginForm);

		return mapping.findForward("orderbylike");

	}


}
