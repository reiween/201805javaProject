package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import action.form.JoinForm;
import action.form.LoginForm;
import action.form.ReviewForm;
import crawl.CrawlBean;
import model.CrawlOutputBean;
import model.ReviewBean;

public class MovieDB {
	public void DBInput(List<CrawlBean> webinfo) throws Exception {
		Connection con = null;

		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost/softgshin", "root", "");
			con.setAutoCommit(false);
			// SQL文を書く
			pstmt = con.prepareStatement(
					"INSERT INTO movie_info(movie_title, movie_date, movie_genre, movie_duration, director, image_url) "
							+ "VALUES(?, ?, ?, ?, ?, ?)");
			for (CrawlBean cbn : webinfo) {
				pstmt.setString(1, cbn.getTitle().replaceAll("～", "@"));
				pstmt.setString(2, cbn.getPublished());
				pstmt.setString(3, cbn.getGenre());
				pstmt.setString(4, cbn.getDuration());
				pstmt.setString(5, cbn.getDirector());
				pstmt.setString(6,
						cbn.getImgurl().substring(cbn.getImgurl().lastIndexOf("/") + 1, cbn.getImgurl().length()));

				pstmt.executeUpdate();

				con.commit();
			}
		} catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}
	public List<CrawlOutputBean> DBrewrite(List<CrawlBean> webinfo) throws Exception {
		List<CrawlOutputBean> rewrite = new ArrayList<CrawlOutputBean>();
		List<CrawlOutputBean> old = new ArrayList<CrawlOutputBean>();
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost/softgshin", "root", "");

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_id, movie_title, movie_date, movie_genre, movie_duration, director, image_url, likeCount, reviewCount FROM movie_info limit 30";

			rs = stmt.executeQuery(sqlStr);

			while(rs.next()) {
				for (CrawlBean cbn : webinfo) {
					if(!cbn.getTitle().equals(rs.getString("movie_title").replaceAll("@", "～"))) {
						CrawlOutputBean output = new CrawlOutputBean();
						output.setTitle(cbn.getTitle());
						output.setGenre(cbn.getGenre());
						output.setDirector(cbn.getDirector());
						output.setDuration(cbn.getDuration());
						output.setImgurl(cbn.getImgurl());
						rewrite.add(output);
					}
					if(!cbn.getTitle().equals(rs.getString("movie_title").replaceAll("@", "～"))) {
						CrawlOutputBean output = new CrawlOutputBean();
						output.setMovie_id(rs.getInt("movie_id"));
						output.setTitle(rs.getString("movie_title").replaceAll("@", "～"));
						output.setGenre(rs.getString("movie_genre"));
						output.setDirector(rs.getString("movie_duration"));
						output.setDuration(rs.getString("director"));
						output.setImgurl(rs.getString("image_url"));
						old.add(output);
					}
				}
			}
			return rewrite;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}
	public void updateLike(int likeCount, int movie_id, DataSource source) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(
					"UPDATE movie_info SET likeCount = ? WHERE movie_id = ?");

			pstmt.setInt(1, likeCount);
			pstmt.setInt(2, movie_id);

			pstmt.executeUpdate();

			con.commit();

		} catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}
	public void updateReviewCount(int reviewCount, int movie_id, DataSource source) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(
					"UPDATE movie_info SET reviewCount = ? WHERE movie_id = ?");

			pstmt.setInt(1, reviewCount);
			pstmt.setInt(2, movie_id);

			pstmt.executeUpdate();

			con.commit();

		} catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

	public List<CrawlOutputBean> DBoutput(DataSource source) throws Exception {
		List<CrawlOutputBean> outputlist = new ArrayList<CrawlOutputBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_id, movie_title, movie_date, movie_genre, movie_duration, director, image_url, likeCount, reviewCount FROM movie_info limit 30";

			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				CrawlOutputBean li = new CrawlOutputBean();
				li.setTitle(rs.getString("movie_title").replaceAll("@", "～"));
				li.setPublished(rs.getString("movie_date"));
				li.setGenre(rs.getString("movie_genre"));
				li.setDuration(rs.getString("movie_duration"));
				li.setDirector(rs.getString("director"));
				li.setImgurl(rs.getString("image_url"));
				li.setMovie_id(rs.getInt("movie_id"));
				li.setLikeCount(rs.getInt("likeCount"));
				li.setReviewCount(rs.getInt("reviewCount"));
				outputlist.add(li);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return outputlist;

	}
	public List<CrawlOutputBean> orderbylike(DataSource source) throws Exception {
		List<CrawlOutputBean> orderbylike = new ArrayList<CrawlOutputBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_id, movie_title, movie_date, movie_genre, movie_duration, director, image_url, likeCount FROM movie_info ORDER BY likeCount DESC";

			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				CrawlOutputBean li = new CrawlOutputBean();
				li.setTitle(rs.getString("movie_title").replaceAll("@", "～"));
				li.setPublished(rs.getString("movie_date"));
				li.setGenre(rs.getString("movie_genre"));
				li.setDuration(rs.getString("movie_duration"));
				li.setDirector(rs.getString("director"));
				li.setImgurl(rs.getString("image_url"));
				li.setMovie_id(Integer.parseInt(rs.getString("movie_id")));
				li.setLikeCount(rs.getInt("likeCount"));
				orderbylike.add(li);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return orderbylike;

	}

	public int Id(String title, DataSource source) throws Exception {
		int movie_id = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_id FROM movie_info WHERE movie_title ='" + title.replaceAll("～", "@") + "'";

			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {

				movie_id = Integer.parseInt(rs.getString("movie_id"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return movie_id;

	}

	public String userId(int login_id, DataSource source) throws Exception {
		String user_id = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT user_id FROM login WHERE login_id ='" + login_id + "'";
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				user_id = rs.getString("user_id");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return user_id;
	}

	public int loginId(String user_id, DataSource source) throws Exception {
		int login_id = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT login_id FROM login WHERE user_id ='" + user_id + "'";
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				login_id = Integer.parseInt(rs.getString("login_id"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return login_id;

	}

	public List<ReviewBean> reviewSearch(int movie_id, DataSource source) throws Exception {
		List<ReviewBean> reviewlist = new ArrayList<ReviewBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT review_id, login_id, review, inp_date  FROM movie_review WHERE movie_id = '" + movie_id + "'";
			rs = stmt.executeQuery(sqlStr);

			while (rs.next()) {
				ReviewBean li = new ReviewBean();
				li.setReview_id(rs.getInt("review_id"));
				li.setLogin_id(Integer.parseInt(rs.getString("login_id")));
				li.setReview(rs.getString("review"));
				li.setInp_date(rs.getString("inp_date"));
				reviewlist.add(li);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return reviewlist;

	}

	public List<ReviewBean> infoSearch(int movie_id, DataSource source) throws Exception {
		List<ReviewBean> infolist = new ArrayList<ReviewBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_title, movie_genre, movie_duration, director, image_url FROM movie_info WHERE movie_id ='"
					+ movie_id + "' ";
			rs = stmt.executeQuery(sqlStr);

			while (rs.next()) {
				ReviewBean cbn = new ReviewBean();
				cbn.setMovie_title(rs.getString("movie_title").replaceAll("@", "～"));
				cbn.setGenre(rs.getString("movie_genre"));
				cbn.setDuration(rs.getString("movie_duration"));
				cbn.setDirector(rs.getString("director"));
				cbn.setImage_url(rs.getString("image_url"));

				infolist.add(cbn);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return infolist;
	}


	public int likeCount(int movie_id, DataSource source) throws Exception {
		int like = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();
			String sqlStr = "SELECT COUNT(movie_like) FROM movie_review WHERE movie_id ='" + movie_id + "' AND movie_like = '1'";
			rs = stmt.executeQuery(sqlStr);
			if(rs.next()) {
					ReviewBean cbn = new ReviewBean();
					cbn.setLike(rs.getInt("COUNT(movie_like)"));
					like = rs.getInt("COUNT(movie_like)");
			} else {
				ReviewBean cbn = new ReviewBean();
				cbn.setLike(0);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return like;
	}
	public int reviewCount(int movie_id, DataSource source) throws Exception {
		int reviewCount = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();
			String sqlStr = "SELECT COUNT(review_id) FROM movie_review WHERE movie_id ='" + movie_id + "'";
			rs = stmt.executeQuery(sqlStr);
			if(rs.next()) {
				ReviewBean cbn = new ReviewBean();
				cbn.setReviewCount(rs.getInt("COUNT(review_id)"));
				reviewCount = rs.getInt("COUNT(review_id)");
			} else {
				ReviewBean cbn = new ReviewBean();
				cbn.setReviewCount(0);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return reviewCount;
	}
	public void reviewInput(ReviewForm rfm, DataSource source) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = source.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(
					"INSERT INTO movie_review(movie_id, login_id, movie_like, review, inp_date) "
							+ "VALUES(?, ?, ?, ?, CURRENT_DATE())");

			pstmt.setInt(1, rfm.getMovie_id());
			pstmt.setInt(2, rfm.getLogin_id());
			pstmt.setInt(3, rfm.getLike());
			if(rfm.getReview().equals("") && rfm.getLike() == 1) {
				pstmt.setString(4, "いいね！");
			}else {
				pstmt.setString(4, rfm.getReview());
			}

			pstmt.executeUpdate();

			con.commit();
		} catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}


	public List<LoginForm> Search(LoginForm login, DataSource source) throws Exception {
		List<LoginForm> list = new ArrayList<LoginForm>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT user_id, passwd FROM login WHERE user_id = '" + login.getId() + "'";

			rs = stmt.executeQuery(sqlStr);

			while (rs.next()) {
				LoginForm li = new LoginForm();
				li.setId(rs.getString("user_id"));
				li.setPw(rs.getString("passwd"));

				list.add(li);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}
	public List<JoinForm> JoinSearch(JoinForm jfm, DataSource source) throws Exception {
		List<JoinForm> joinlist = new ArrayList<JoinForm>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT user_id, passwd FROM login WHERE user_id = '" + jfm.getId() + "'";

			rs = stmt.executeQuery(sqlStr);

			while (rs.next()) {
				JoinForm bn = new JoinForm();
				bn.setId(rs.getString("user_id"));

				joinlist.add(bn);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return joinlist;
	}
	public void loginInput(JoinForm jfm, DataSource source) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			con = source.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(
					"INSERT INTO login(user_id, passwd, inp_date) "
							+ "VALUES(?, ?, CURRENT_DATE())");

			pstmt.setString(1, jfm.getId());
			pstmt.setString(2, jfm.getPw());

			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}


	}
	public List<ReviewBean> reviewfind(int review_id, DataSource source) throws Exception {
		List<ReviewBean> reviewfind = new ArrayList<ReviewBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT review_id, login_id, review, inp_date  FROM movie_review WHERE review_id = '" + review_id + "'";
			rs = stmt.executeQuery(sqlStr);

			while (rs.next()) {
				ReviewBean li = new ReviewBean();
				li.setReview_id(rs.getInt("review_id"));
				li.setLogin_id(Integer.parseInt(rs.getString("login_id")));
				li.setReview(rs.getString("review"));
				li.setInp_date(rs.getString("inp_date"));
				reviewfind.add(li);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return reviewfind;

	}
	public void reviewDelete(int review_id, DataSource source) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(
					"DELETE FROM movie_review WHERE review_id = ?");

			pstmt.setInt(1, review_id);

			pstmt.executeUpdate();

			con.commit();

		} catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	public List<ReviewBean> movieSearch(String search, DataSource source) throws Exception {
		List<ReviewBean> searchlist = new ArrayList<ReviewBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = source.getConnection();

			stmt = con.createStatement();

			String sqlStr = "SELECT movie_title, movie_genre, movie_duration, director, image_url FROM movie_info";

			if(!search.equals("")) {
				sqlStr += "WHERE movie_title LIKE '%" + search + "%'";
			}

			rs = stmt.executeQuery(sqlStr);


			while (rs.next()) {
				ReviewBean cbn = new ReviewBean();
				cbn.setMovie_title(rs.getString("movie_title").replaceAll("@", "～"));
				cbn.setGenre(rs.getString("movie_genre"));
				cbn.setDuration(rs.getString("movie_duration"));
				cbn.setDirector(rs.getString("director"));
				cbn.setImage_url(rs.getString("image_url"));

				searchlist.add(cbn);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return searchlist;
	}


}
