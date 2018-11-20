package action;

import java.util.ArrayList;
import java.util.List;

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

import action.form.JoinForm;
import dao.MovieDB;

public class JoinAction extends Action {

	public ActionForward execute(ActionMapping mapping
            , ActionForm form
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// キャンセルを押した場合の処理
		if(this.isCancelled(request)) {

			// セッションの初期化
			if (session != null) {
				session.invalidate();
			}
			return mapping.findForward("back");
		}
		JoinForm jfm = (JoinForm) form;

		MovieDB mdb = new MovieDB();

		DataSource source = getDataSource(request);
		List<JoinForm> joinlist = mdb.JoinSearch(jfm, source);

		if(joinlist == null) {
			joinlist = new ArrayList<JoinForm>();
		}

		String id = request.getParameter("id");
		String passwd = request.getParameter("pw");
		String pw_profit = request.getParameter("pw_profit");

		if(id.equals("")) {
			ActionMessages errors = new ActionMessages();
			errors.add("join_id.required", new ActionMessage("error.join_id.required"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}
		if(passwd.equals("")) {
			ActionMessages errors = new ActionMessages();
			errors.add("join_pw.required", new ActionMessage("error.join_pw.required"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

		if(!passwd.equals(pw_profit)){
			ActionMessages errors = new ActionMessages();
			errors.add("pw.notcorrect", new ActionMessage("error.pw.notcorrect"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

		for(JoinForm ck : joinlist) {
			if(id.equals(ck.getId())) {
				ActionMessages errors = new ActionMessages();
				errors.add("user_id.remote", new ActionMessage("error.user_id.remote"));
				saveErrors(request, errors);
				return mapping.findForward("error");
			}
		}
		session.setAttribute("jfm", jfm);
		//
		return mapping.findForward("done");

	}

}
