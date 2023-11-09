package Animation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Quarterly {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://anilife.live/quarter").get();
		Elements category = doc.select("div.page li a");
		HashMap<String, String> map = new HashMap<>();
		for(int i = 0;i<17;i++) {
			Element e = category.get(i);
			map.put(e.text(), e.attr("href"));
		}
		System.out.println(map);
		List<String> list = new ArrayList<>(map.keySet());
		System.out.println(list);
		try {
		for(int i = 0;i<17;i++) {
			int paging = 1;
			java.sql.Statement st = null;
			Connection con = null;
			String sql = "";
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
					"root", "4612");
			st = con.createStatement();
			st.executeUpdate("use animation");
			String url = map.get(list.get(i));
			doc = Jsoup.connect("https://anilife.live"+url+"/"+paging).get();
			Elements page = doc.select("div.pagination");
			if(page.text().equals("")) {
				sql = "insert into quarter(title, img, quart) values(?,?,?)";
				String left = map.get(list.get(i)).substring(map.get(list.get(i)).length()-7,map.get(list.get(i)).length()-2);
				String right = map.get(list.get(i)).split("")[map.get(list.get(i)).length()-1];
				String s = left+"-"+right;
				Elements img  = doc.select("div.bsx img");
				Elements title = doc.select("div.tt h2");
				int idx = 0;
				for(Element e: img) {
					String t = title.get(idx).text().toString();
					if(isTitleAlready(con, t)) {
						idx++;
						continue;
					}
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1,t);
					pst.setString(2, e.attr("src").toString());
					pst.setString(3,s);
					pst.execute();
					pst.close();
					idx++;
				}
				idx = 0;
				
			}else {
				while(true) {
					sql = "insert into quarter(title, img, quart) values(?,?,?)";
					doc = Jsoup.connect("https://anilife.live"+url+"/"+paging).get();
					page = doc.select("div.pagination");
					if(page.text().equals("")) {
						paging = 1;
						break;
					}
					Elements img  = doc.select("div.bsx img");
					Elements title = doc.select("div.tt h2");
					int idx = 0;
					String left = map.get(list.get(i)).substring(map.get(list.get(i)).length()-5,map.get(list.get(i)).length()-1);
					String right = map.get(list.get(i)).split("")[map.get(list.get(i)).length()-1];
					String s = left+"-"+right;
					for(Element e: img) {
						String t = title.get(idx).text().toString();
						if(isTitleAlready(con, t)) {
							idx++;
							continue;
						}
						PreparedStatement pst = con.prepareStatement(sql);
						pst.setString(1, t);
						pst.setString(2, e.attr("src").toString());
						pst.setString(3,s);
						pst.execute();
						pst.close();
						idx++;
					}
					System.out.println("paging : "+paging);
					paging++;
					idx = 0;
				}
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static boolean isTitleAlready(Connection con, String title) {
		int count = 0;
		String query = "SELECT COUNT(*) FROM quarter WHERE title = ?";
	    PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, title);
		    ResultSet resultSet = preparedStatement.executeQuery();
		    resultSet.next();
		    count = resultSet.getInt(1);
		    preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    return count > 0;
	}
}
