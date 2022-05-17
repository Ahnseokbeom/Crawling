

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class D_name {
	public static void main(String[] args) throws IOException{
		String sql;
		int page = 1;
		int n = 1;
			while(page<=11) {
				try {
					java.sql.Statement st = null;
					ResultSet rs = null;
					Connection con = null;
					// mysql 연결
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
							"root", "4612");
					st = con.createStatement();
					// database 선택
					st.executeUpdate("use DiconPJ;");
//					 각 티어별 페이지 수 계산
		Document doc = Jsoup.connect("http://gamechart100.com/bbs/board.php?bo_table=B11&page="+page).get();
		// 게임 이름
		Elements c = doc.select("div[class=\"mw_basic_list_subject_desc\"] a");
		String strr = c.text().replaceAll("^[+0-9]", " ");
		String[] s = strr.split(" ");
		String name = null;
		for(int i = 0;i<s.length;i++) {
			if(s[i].equals("히어로즈")) name = "히어로즈 오브 더 스톰";
			if(s[i].equals("") || s[i].equals("(PC)")) {
				continue;
			}else if(s[i].equals("+1")) {
				continue;}
			else if(s[i].equals("+2")) {
				continue;}
			else if(s[i].equals("+3")) {
				continue;}
			else if(s[i].equals("+4")) {
				continue;}
			else if(s[i].equals("+5")) {
				continue;
				}
			else if(s[i].equals("히어로즈")) {
				continue;
				}
			else if(s[i].equals("오브")) {
				continue;
				}
			else if(s[i].equals("더")) {
				continue;
				}
			else if(s[i].equals("스톰")) {
				continue;
				}
			else if(n==22) {
				n++;
				continue;
				}else {
				name = s[i];
				//"update Problem set rate = ? where ID = ?"
				sql = "update games set name = ? where id = ? ";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, name);
				pst.setInt(2, n);
				pst.execute();
				pst.close();
				n++;
			}
			System.out.println(name);
		}
		page++;
		}catch(Exception e) {
			e.printStackTrace();
			}
		}

	}
}
