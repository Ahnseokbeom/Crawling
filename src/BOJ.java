import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BOJ {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://www.acmicpc.net/school/ranklist/309").get();
		Elements e = doc.select("div.table-responsive td");
		System.out.println(e.get(1).text());
		System.out.println(e.get(7).text());
		Document doc2 = Jsoup.connect("https://www.acmicpc.net/problemset?user=eoehd1ek&user_solved=1").get();
		Elements page = doc2.select("div.text-center");
		Elements e2 = doc2.select("div.table-responsive td");
		Elements e3 = doc2.select("div.table-responsive td.list_problem_id");
		System.out.println(e3.text());
		String[] proStr = e3.text().split(" ");
		System.out.println(Arrays.toString(proStr));
//		System.out.println(e2.get(0).text());
//		System.out.println(e2.get(6).text());
		System.out.println(page.text());
		
	}

}
