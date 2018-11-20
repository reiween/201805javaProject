package action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class ReviewForm extends ValidatorForm {
	private int movie_id;
	private int login_id;
	private int like;
	private String review;
	private String inp_date;
	private String movie_title;
	private String image_url;
	private String link;
	private String published;
	private String genre;
	private String duration;
	private String director;
	private String cast;
	private String publishing;
	private String country;

	public ReviewForm() {
		movie_id = 0;
		like = 0;
		review = "";
		inp_date = "";
		movie_title = "";
		image_url = "";
		published = "";
		genre = "";
		duration = "";
		director = "";
		cast = "";
		publishing = "";
		country = "";
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public int getLogin_id() {
		return login_id;
	}
	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getInp_date() {
		return inp_date;
	}
	public void setInp_date(String inp_date) {
		this.inp_date = inp_date;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public String getPublishing() {
		return publishing;
	}
	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);

		if (review.equals("")) {
			errors.add("error.review.required", new ActionMessage("error.review.required"));
		}
		return errors;
	}

}
