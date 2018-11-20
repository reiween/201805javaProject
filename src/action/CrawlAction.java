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

public class CrawlAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		DataSource source = getDataSource(request);

		LoginForm loginForm = (LoginForm) session.getAttribute("loginForm");
		MovieDB mdb = new MovieDB();
		try {
			// データを格納させるためのリストを宣言
			List<CrawlOutputBean> outputlist = mdb.DBoutput(source);

			// DBで取得したmovie_idで検索していいねの数をカウント
			for (CrawlOutputBean cbn : outputlist) {
				int movie_id = cbn.getMovie_id();
				int likeCount = mdb.likeCount(movie_id, source);
				int reviewCount = mdb.reviewCount(movie_id, source);
				//カウントしたいいねの数をデータベースに入力
				mdb.updateLike(likeCount, movie_id, source);
				mdb.updateReviewCount(reviewCount, movie_id, source);
			}

			// 入力したいいねの数まで改めに取得
			outputlist = mdb.DBoutput(source);

			// ログインセッションを続けるための宣言
			String loginId = loginForm.getId();
//			if(loginId.equals("")){
//				session.invalidate();
//				return mapping.findForward("needlogin");
//			}
			loginForm.setId(loginId);

			// セッションにデータリストとログイン情報を格納
			session.setAttribute("outputlist", outputlist);
			session.setAttribute("loginFrom", loginForm);
		} catch (NullPointerException e) {

			// ログインセッションがnullの場合ログイン画面に遷移
			session.invalidate();
			return mapping.findForward("needlogin");
		}

		return mapping.findForward("index");
	}

}
