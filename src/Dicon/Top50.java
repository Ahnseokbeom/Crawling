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

public class Top50 {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://www.gamemeca.com/ranking.php").get();
		Elements img = doc.select("tr.ranking-table-rows td img");
		Elements name = doc.select("tr.ranking-table-rows td div.game-name");
		Elements url = doc.select("tr.ranking-table-rows td div.game-name a");
		Elements typ = doc.select("tr.ranking-table-rows td div.game-info");
		String[] urls = new String[50];
		for(int i = 0;i<urls.length;i++) {
			urls[i] = url.get(i).attr("href");
		}
		String[] Gname = new String[50];

		for(int i = 0;i<50;i++) {
			Gname[i] = name.get(i).text();
		}
		String[] imgL = new String[50];
		for(int i = 0;i<50;i++) {
			imgL[i] = img.get(i).attr("src");
		}
		System.out.println(Arrays.toString(Gname));
		System.out.println(Arrays.toString(imgL));
		String[] type = new String[50];
		for(int i = 0;i<50;i++) {
			String st = typ.get(i).text().replaceAll("[^스포츠a-zA-Z]","");
			if(st.equals(" ") || st.length()<3) type[i] = "액션";
			else type[i] = st.substring(st.length()-3,st.length());
		}
		System.out.println(Arrays.toString(type));
		try {
			String gamename = "";
			String imgurl = "";
			String gametype = "";
			String sql = "";
			String Gameurl = "";
			java.sql.Statement st = null;
//			ResultSet rs = null;
			Connection con = null;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
					"root", "4612");
			st = con.createStatement();
			st.executeUpdate("use DiconPJ;");
			for(int i = 0;i<50;i++) {
				Document Gurl = Jsoup.connect("https://www.gamemeca.com/"+urls[i]).get();
				Elements Gaurl = Gurl.select("div.GmaeInfoTxt p.btmSocial a");
				int heart = (int)(Math.random()*10+1);
				gamename = Gname[i];
				gametype = type[i];
				imgurl = imgL[i];
				Gameurl = Gaurl.attr("href");
//				sql = "insert into Game values(?,?,?,?,?)";
				sql = "update Game set name = ?, type = ?, image = ?, heart = ?, url = ? where ranking = ?";
				PreparedStatement pst = con.prepareStatement(sql);
				System.out.println(i+1);
				pst.setString(1, gamename);
				pst.setString(2, gametype);
				pst.setString(3, imgurl);
				pst.setInt(4, heart);
				pst.setString(5, Gameurl);
				pst.setInt(6, i+1);
				pst.execute();
				pst.close();
			}
//			rs = st.executeQuery("select * from Game;");
//			while(rs.next()) {
//				String idx = rs.getString("USER_ID");
//				String pro = rs.getString("PROBLEM_ID");
//				System.out.println(idx+" "+pro);
//			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
