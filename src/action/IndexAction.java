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

public class IndexAction extends LookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		// Mapオブジェクト生成し、メッセージキーとメソッド名格納
		Map<String, String> map = new HashMap<String, String>();
		map.put("choice", "nextPage");
		map.put("logout", "logoutPage");
		map.put("goindex", "indexPage");
		map.put("orderbylike", "orderbylikePage");
		return map;
	}

	public ActionForward nextPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// セッションの宣言
			HttpSession session = request.getSession();

			// データソースの取得
			DataSource source = getDataSource(request);

			// hiddenタグの値を取得


			// ログイン情報を取得
			LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

			// データベース接続クラスのインスタンス化
			MovieDB mdb = new MovieDB();

			// ログイン情報をまた格納
			String loginId = loginForm.getId();
			loginForm.setId(loginId);

			// 映画IDmovie_idの取得
			int movie_id = Integer.parseInt(request.getParameter("movie_id"));

			// 取得した映画IDで検索したデータをリストに格納
			// 映画レビュー
			List<ReviewBean> reviewList = mdb.reviewSearch(movie_id, source);

			// 映画情報
			List<ReviewBean> infoList = mdb.infoSearch(movie_id, source);

			// いいねの数
			int likeCount = mdb.likeCount(movie_id, source);

			// 取得したログインIDでユーザIDを取得、beansに格納
			for (ReviewBean rbn : reviewList) {
				int login_id = rbn.getLogin_id();
				String user_id = mdb.userId(login_id, source);
				rbn.setUser_id(user_id);

			}
			// 各情報をセッションに格納
			session.setAttribute("reviewList", reviewList);
			session.setAttribute("infoList", infoList);
			session.setAttribute("likeCount", likeCount);
			session.setAttribute("loginForm", loginForm);

			ReviewBean rbn = new ReviewBean();
			rbn.setLike(likeCount);
			rbn.setMovie_id(movie_id);
			session.setAttribute("rbn", rbn);
			return mapping.findForward("information");
		} catch (NullPointerException e) {
			// ログインセッションがnullの場合ログイン画面に遷移
			HttpSession session = request.getSession();
			session.invalidate();
			return mapping.findForward("needlogin");
		}
	}

	public ActionForward logoutPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ログアウトボタンを押すとセッションを初期化させてログイン画面に遷移
		HttpSession session = request.getSession();
		session.removeAttribute("LoginForm");
		return mapping.findForward("back");
	}

	public ActionForward orderbylikePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

		session.setAttribute("loginForm", loginForm);

		return mapping.findForward("orderbylike");

	}
	public ActionForward indexPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");

		session.setAttribute("loginForm", loginForm);

		return mapping.findForward("goindex");

	}


}
