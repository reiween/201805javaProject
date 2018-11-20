package crawl;

public class CrawlBean {
	private String title;
	private String imgurl;
	private String link;
	private String published;
	private String genre;
	private String duration;
	private String director;
	private String cast;
	private String publishing;
	private String country;

	public CrawlBean() {
		title = "";
		imgurl = "";
		link = "";
		published = "";
		genre = "";
		duration = "";
		director = "";
		cast = "";
		publishing = "";
		country = "";
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
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

}
