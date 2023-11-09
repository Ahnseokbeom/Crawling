package Animation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TopAnimation {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://anilife.live/top20").get();
		Elements index = doc.select("span.epx");
		Elements title = doc.select("div.tt h2");
		try {
			java.sql.Statement st = null;
			Connection con = null;
			String sql = "";
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
					"root", "4612");
			st = con.createStatement();
			st.executeUpdate("use animation");
		for(int i = 0;i<20;i++) {
			sql = "insert into top(name, episode) values(?,?)";
			String t = title.get(i).text().toString();
			if(t.contains("&#44;")) {
				t = t.replaceAll("&#44;", ",");
			}
			if(index.get(i).text().contains("/")) {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, t);
				pst.setString(2, index.get(i).text().toString().split(" / ")[0]);
				pst.execute();
				pst.close();
			}else{
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, t);
				pst.setString(2, index.get(i).text().toString());
				pst.execute();
				pst.close();
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

