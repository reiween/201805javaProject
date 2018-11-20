package model;

import java.io.Serializable;

public class ReviewBean implements Serializable {
	private int movie_id;
	private int login_id;
	private int like;
	private int review_id;
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
	private String user_id;
	private int reviewCount;

	public ReviewBean() {
		movie_id = 0;

		like = 0;
		review_id = 0;
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
		user_id="";
		reviewCount=0;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

}
