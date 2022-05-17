

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class D_kind {
	public static void main(String[] args) throws IOException{
		String sql;
		//page = 11
		int page = 1;
		// 게임 장르
		String k = null;
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
				// 각 티어별 페이지 수 계산
		// 게임 장르
		int a1 = 0;
		Document doc = Jsoup.connect("http://gamechart100.com/bbs/board.php?bo_table=B11&page="+page).get();
		Elements a = doc.select("div[class=\"mw_basic_list_desc\"]");
		String kind = a.text().replaceAll("[공식 0-9위 | :]", " ");
		String[] kinds = kind.split(" ");
		for(int i = 0;i<kinds.length;i++) {
			if(kinds[i].equals("장르")) {
				k = kinds[i+3];
//				System.out.println(k);
				sql = "insert into games(kind) values(?)";
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, k);
				pst.execute();
				pst.close();
			}

		}

		page++;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
