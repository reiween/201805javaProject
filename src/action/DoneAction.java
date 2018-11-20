package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import action.form.JoinForm;
import dao.MovieDB;

public class DoneAction extends Action {

	public ActionForward execute(ActionMapping mapping
            , ActionForm form
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		JoinForm jfm = (JoinForm) session.getAttribute("jfm");

		// データベース入力のため接続クラスを読み出す
		MovieDB mdb = new MovieDB();
		DataSource source = getDataSource(request);


		if(this.isCancelled(request)) {

			// セッションの初期化
			if (session != null) {
				session.invalidate();
			}
			return mapping.findForward("back");
		}

		try {
			mdb.loginInput(jfm, source);

		} catch (Exception e) {
			e.printStackTrace();
		}


		return mapping.findForward("joinEnd");
	}

}
