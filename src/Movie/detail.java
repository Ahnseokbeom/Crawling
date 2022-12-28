package Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		try {
			String director = "";
			String actor = "";
			String genre = "";
			String deta = "";
			String sql = "";
			java.sql.Statement st = null;
//			ResultSet rs = null;
			Connection con = null;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
					"root", "4612");
			st = con.createStatement();
			st.executeUpdate("use movie;");
			for(i = 0;i<19;i++) {
				Document doc2 = Jsoup.connect(detail[i]).get();
				Elements a = doc2.select("div.spec");
				Elements details = doc2.select("div.sect-story-movie");
				String[] a2 = a.text().split("/ 배우 : ");
				String[] a3 = a2[1].split(" 장르 : ");
				String[] a4 = a3[1].split(" / ");
				director = a2[0];
				actor = a3[0];
				genre = a4[0];
				deta = details.text();
//				sql = "insert into Game values(?,?,?,?,?)";
				sql = "insert into detail values(?,?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(sql);
				System.out.println(i+1);
				pst.setInt(1, i+1);
				pst.setString(2, director);
				pst.setString(3, actor);
				pst.setString(4, genre);
				pst.setString(5, deta);
				pst.execute();
				pst.close();
				System.out.println("direcort : "+director);
				System.out.println("actor : "+actor);
				System.out.println("genre : "+genre);
				System.out.println("deta : "+deta);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
