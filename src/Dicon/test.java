package Dicon;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class test {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://www.gamemeca.com/ranking.php").get();
		Elements img = doc.select("tr.ranking-table-rows td img");
		Elements name = doc.select("tr.ranking-table-rows td div.game-name");
		Elements name2 = doc.select("tr.ranking-table-rows td div.game-name a");
		Elements typ = doc.select("tr.ranking-table-rows td div.game-info");
		String[] Gname = new String[50];
		for(int i = 0;i<Gname.length;i++) {
			Gname[i] = name2.get(i).attr("href");
		}
		System.out.println(Arrays.toString(Gname));
		Document doc2 = Jsoup.connect("https://www.gamemeca.com"+Gname[0]).get();
		Elements url = doc2.select("div.GmaeInfoTxt p.btmSocial a");
		System.out.println(url.attr("href"));
	}
}
