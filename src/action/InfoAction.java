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

public class InfoAction extends LookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		// Mapオブジェクト生成し、メッセージキーとメソッド名格納
		Map<String, String> map = new HashMap<String, String>();
		map.put("review", "reviewPage");
		map.put("back", "backPage");
		map.put("delete", "deletePage");
		return map;
	}

	public ActionForward reviewPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);
			MovieDB mdb = new MovieDB();
			// beansのインスタンス化
			ReviewBean rbn = (ReviewBean) session.getAttribute("rbn");

			// ログイン情報の取得
			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
			String loginId = loginForm.getId();
			loginForm.setId(loginId);

			// hiddenタグで取得したタイトル情報を渡して映画IDを検索
			String title = rbn.getMovie_title();
			int movie_id = rbn.getMovie_id();

			// 検索した映画IDで映画の情報を取得
			List<ReviewBean> infoList = mdb.infoSearch(movie_id, source);
			rbn = new ReviewBean();
			rbn.setMovie_title(title);

			// 取得した情報をセッションに格納
			session.setAttribute("rbn", rbn);
			request.setAttribute("movie_id", movie_id);
			session.setAttribute("infoList", infoList);
			session.setAttribute("loginForm", loginForm);
			return mapping.findForward("review");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}

	public ActionForward backPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("back");
	}

	public ActionForward deletePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			DataSource source = getDataSource(request);

			// hiddenタグのレビューIDを取得
			int review_id = Integer.parseInt(request.getParameter("review_id"));
			MovieDB mdb = new MovieDB();

			// ログイン情報を取得
			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
			String loginId = loginForm.getId();
			loginForm.setId(loginId);

			// レビューIDでレビューを検索
			List<ReviewBean> reviewFind = mdb.reviewfind(review_id, source);

			// ログイン情報のログインIDでユーザIDを検索
			for (ReviewBean rbn : reviewFind) {
				int login_id = rbn.getLogin_id();
				String user_id = mdb.userId(login_id, source);
				rbn.setUser_id(user_id);

			}

			session.setAttribute("reviewFind", reviewFind);
			session.setAttribute("loginForm", loginForm);

			return mapping.findForward("delete");
		} catch (NullPointerException e) {
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}
}
