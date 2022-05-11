import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class test {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://solved.ac/profile/asb0313/solved").get();
		Elements e = doc.select("div[class=\"StickyTable__Cell-sc-45ty5n-1 bqklaG sticky-table-cell\"]");
		System.out.println(e.size());
		System.out.println(e.text());
		System.out.println("hello");
	}

}
