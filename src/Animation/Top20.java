package Animation;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Top20 {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://anilife.live/top20").get();
		Elements index = doc.select("span.epx");
		Elements title = doc.select("div.tt h2");
		Elements img = doc.select("div.bsx img");
		int idx = 0;
		for(Element e : img) {
			if(index.get(idx).text().contains("/")) {
				System.out.println(index.get(idx).text().toString().split(" / ")[0]+" "+title.get(idx++).text()+" "+e.attr("src"));
			}else System.out.println(index.get(idx).text()+" "+title.get(idx++).text()+" "+e.attr("src"));
		}
	}
}
