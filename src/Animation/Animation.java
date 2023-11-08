package Animation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.cj.xdevapi.JsonParser;

public class Animation {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://anilife.live/dailyani").get();
		Elements title = doc.select("a.datebox-desktop-table-content div.title");
		Elements img = doc.select("a.datebox-desktop-table-content img");
		int idx = 0;
		for(Element e : img) {
			if(title.get(idx).text().contains("&#44;")){
				System.out.print(e.attr("src")+" "+title.get(idx++).text().replaceAll("&#44;", ",")+"\n");
			}else if(title.get(idx).text().contains("&#39;")) {
				System.out.print(e.attr("src")+" "+title.get(idx++).text().replaceAll("&#39;", "'")+"\n");
			}else System.out.print(e.attr("src")+" "+title.get(idx++).text()+"\n");
		}
	}
}
