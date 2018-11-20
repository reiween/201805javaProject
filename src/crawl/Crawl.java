package crawl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

import dao.MovieDB;

public class Crawl {

	public static void main(String[] args) {

		List<CrawlBean> webinfo = new ArrayList<CrawlBean>();
		try {
			// 持ってくるhttpをセッティング
			HttpPost http = new HttpPost("https://movie.jorudan.co.jp/cinema/");

			// 取得を実行するクライアントオブジェクト生成
			HttpClient httpClient = HttpClientBuilder.create().build();

			// 実行及び実行データをResponseオブジェクトに格納
			HttpResponse response = httpClient.execute(http);

			// Responseされたデータの中、DOMデータを取得してEntityに格納
			HttpEntity entity = response.getEntity();

			// Charsetを調べるためDOMのコンタントタイプを格納した後、Charsetを取得
			ContentType contentType = ContentType.getOrDefault(entity);

			Charset charset = contentType.getCharset();

			// DOMデータを1行ずつ読むためReaderに格納
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

			// 取得したDOMデータを格納するため宣言
			StringBuffer sb = new StringBuffer();

			// DOMデータ取得
			String line = "";

			while ((line = br.readLine()) != null) {

				sb.append(line + "\n");

			}

			String rawData = sb.toString();
			String item = rawData.substring(rawData.indexOf("card\""), rawData.indexOf("ページ"));

			String[] split = item.split("</article>");

			for (int i = 0; i < split.length - 1; i++) {
				String tl = split[i];
				String link = tl.substring(tl.indexOf("href") + 6, tl.indexOf("title") - 2);
				try {
					// 持ってくるhttpをセッティング
					http = new HttpPost("https://movie.jorudan.co.jp" + link);

					// 取得を実行するクライアントオブジェクト生成
					httpClient = HttpClientBuilder.create().build();

					// 実行及び実行データをResponseオブジェクトに格納
					response = httpClient.execute(http);

					// Responseされたデータの中、DOMデータを取得してEntityに格納
					entity = response.getEntity();

					// Charsetを調べるためDOMのコンタントタイプを格納した後、Charsetを取得
					contentType = ContentType.getOrDefault(entity);

					charset = contentType.getCharset();

					// DOMデータを1行ずつ読むためReaderに格納
					br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

					// 取得したDOMデータを格納するため宣言
					sb = new StringBuffer();

					// DOMデータ取得
					line = "";

					while ((line = br.readLine()) != null) {

						sb.append(line + "\n");

					}
					CrawlBean cbn = new CrawlBean();
					rawData = sb.toString();

					String imageURL = rawData.substring(rawData.indexOf("introduction"),
							rawData.indexOf("<p class=\"text\">"));
					String imu = imageURL.substring(imageURL.indexOf("href") + 6, imageURL.indexOf("data") - 2);
					cbn.setImgurl(imu);
					String imgUrl = "https://movie.jorudan.co.jp" + imu;
					URL url = new URL(imgUrl);

					String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());

					String ext = imgUrl.substring(imgUrl.lastIndexOf('.') + 1, imgUrl.length());

					BufferedImage img = ImageIO.read(url);

					ImageIO.write(img, ext, new File("C:\\workspace\\softgShin\\WebContent\\image\\" + fileName));

					// if文で必要なデータを取得
					String info = rawData.substring(rawData.indexOf("@context"), rawData.indexOf("\"dateCreated") + 20);
					if (info.contains("name") && info.contains("image")) {
						String title = info.substring(info.indexOf("name") + 7, info.indexOf("image") - 5);
						cbn.setTitle(title);
					}
					if (info.contains("dataPublished") && info.contains("genre")) {
						String published = info.substring(info.indexOf("datePublished"), info.indexOf("genre"));
						cbn.setPublished(published);
					}
					if (info.contains("genre") && info.contains("duration")) {
						String genre = info.substring(info.indexOf("genre") + 8, info.indexOf("duration") - 5);
						cbn.setGenre(genre);
					} else if (info.contains("genre") && info.contains("aggregateRating")) {
						String genre = info.substring(info.indexOf("genre") + 8, info.indexOf("Rating") - 14);
						cbn.setGenre(genre);
					} else if (info.contains("genre") && info.contains("contentRating")) {
						String genre = info.substring(info.indexOf("genre") + 8, info.indexOf("Rating") - 12);
						cbn.setGenre(genre);
					} else {
						String genre = info.substring(info.indexOf("genre") + 8, info.indexOf("director") - 5);
						cbn.setGenre(genre);
					}

					if (info.contains("duration") && info.contains("aggregateRating")) {
						if (info.contains("contentRating")) {
							String duration = info.substring(info.indexOf("duration") + 11,
									info.indexOf("contentRating") - 5);
							cbn.setDuration(duration);
						} else {
							String duration = info.substring(info.indexOf("duration") + 11,
									info.indexOf("Rating") - 14);
							cbn.setDuration(duration);
						}
					} else if (info.contains("duration") && info.contains("contentRating")) {
						String duration = info.substring(info.indexOf("duration") + 11,
								info.indexOf("contentRating") - 5);
						cbn.setDuration(duration);
					}
					if (info.contains("Person") && info.contains("dateCreated")) {
						String director = info.substring(info.indexOf("Person") + 20, info.indexOf("dateCreated") - 8);
						cbn.setDirector(director);
					}
					webinfo.add(cbn);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			MovieDB mdb = new MovieDB();
			mdb.DBInput(webinfo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
