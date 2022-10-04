import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BOJ {
	public static void main(String[] args) throws IOException{
//		Document doc = Jsoup.connect("https://solved.ac/problems/level/"+1).get();
//		Elements page = doc.select("div[class=\"css-18lc7iz\"] a");
//		System.out.println(page.text());
//		String[] str = page.text().split(" ");
//		System.out.println(str[str.length-1]);
//		Document doc1 = Jsoup.connect("https://solved.ac/problems/level/0?page=1").get();
//		Elements t = doc1.select("div[class=\"css-qijqp5\"] td");
//		System.out.print(t.get(0).text()+" ");
//		System.out.println(t.get(1).text());
//		System.out.print(t.get(4).text()+" ");
//		System.out.println(t.get(5).text());
		Document doc = Jsoup.connect("https://solved.ac/profile/shg9411/solved?page=1").get();
		Elements page = doc.select("div[class=\"css-qijqp5\"] td");
		Elements pa = doc.select("div[class=\"css-18lc7iz\"] a");
		System.out.println(page.get(0).text());
		System.out.println(page.get(4).text());
		System.out.println(pa.text());
		Document doc2 = Jsoup.connect("https://solved.ac/ranking/o/309?page="+1).get();
		Elements name = doc2.select("div[class=\"css-qijqp5\"] td");
		System.out.println(name.get(2).text());
		System.out.println(name.get(8).text());
		Document doc3 = Jsoup.connect("https://solved.ac/profile/shg9411/solved").get();
		Elements pro = doc3.select("div[class=\"css-qijqp5\"] td");
		System.out.println(pro.get(0).text());
		System.out.println(pro.get(4).text());
		Document doc4 = Jsoup.connect("https://solved.ac/profile/"+name.get(2).text()+"/solved?page=1").get();
		Elements problem = doc4.select("div[class=\"css-qijqp5\"] td");
		System.out.println(problem.size()/4);
		
	}

}
