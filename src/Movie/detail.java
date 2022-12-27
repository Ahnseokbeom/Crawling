package Movie;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class detail {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/").get();
		Elements movieDetail = doc.select("div.box-contents a");
		String[] detail = new String[19];
		int i = 0;
		for(Element e : movieDetail) {
			if(e.attr("href").charAt(1)=='m') {
			detail[i++] = "http://www.cgv.co.kr"+e.attr("href");
			}
		}
		System.out.println(Arrays.toString(detail));
		Document doc2 = Jsoup.connect(detail[0]).get();
		Elements a = doc2.select("div.spec");
		Elements details = doc2.select("div.sect-story-movie");
		System.out.println(a.text());
		String[] a2 = a.text().split("/");
		String[] a3 = a2[1].split(" 장르 :");
		System.out.println(Arrays.toString(a2));
		System.out.println(Arrays.toString(a3));
		System.out.println(a3[1]);
//		System.out.println(a2[1]);
		
		System.out.println(details.text());
	}
}
