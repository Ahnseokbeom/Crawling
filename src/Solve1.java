

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Solve1 {
	public static void main(String[] args) throws IOException{
		String sql;
		// 유저 ID
		String id = null;
		// 문제 번호
		int num = 0;
		// 성공회대학교 학생목록
		int number = 1;
//		while(number<=2) {
		Document doc = Jsoup.connect("https://solved.ac/ranking/o/309?page=1").get();
		Elements name = doc.select("div[class=\"css-qijqp5\"] td");
		// 21*6을 더해주면서 4개를 동시에 크롤링
		int n = 14;
		// 학생의 인원 수
		int nick = 1;
		// 21번씩 4번 반복하여 크롤링 시작
		while(nick <= 50) {
			// 유저마다 페이지 수 계산
			Document doc2 = Jsoup.connect("https://solved.ac/profile/"+name.get(n).text()+"/solved").get();
			Elements page = doc2.select("div[class=\"css-18lc7iz\"] a");
			String[] str = page.text().split(" ");
			System.out.println(nick+" "+str[str.length-1]);
			try {
				java.sql.Statement st = null;
				ResultSet rs = null;
				Connection con = null;
				// mysql 연결
				con = DriverManager.getConnection("jdbc:mysql://132.145.93.241:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
						"Project", "testing00");
				st = con.createStatement();
				// database 선택
				st.executeUpdate("use swp;");
			
				// 유저당 페이지수만큼 반복
				for(int i = 1;i<=Integer.parseInt(str[str.length-1]);i++) {
//					System.out.println(i);
					// 각 페이지의 Problem
				Document doc3 = Jsoup.connect("https://solved.ac/profile/"+name.get(n).text()+"/solved?page="+i).get();
				Elements problem = doc3.select("div[class=\"css-qijqp5\"] td");
				// 문제 번호 뽑아낼 변수
					int k = 0;
					for(int j = 1; j<=50;j+=1) {
						id = name.get(n).text();
						num = Integer.parseInt(problem.get(k).text());
						try {
							sql = "insert into solve(USER_ID, PROBLEM_ID) values(?, ?)";
							PreparedStatement pst = con.prepareStatement(sql);
							pst.setString(1, id);
							pst.setInt(2, num);
							pst.execute();
							pst.close();
							}catch(Exception e) {
								continue;
							}
						k+=4;
						}
					}
				n+=6;
				nick++;
				// 전체 데이터베이스 조회
//				rs = st.executeQuery("select * from solve;");
//				while(rs.next()) {
//					String idx = rs.getString("USER_ID");
//					String pro = rs.getString("PROBLEM_ID");
//					System.out.println(idx+" "+pro);
//				}
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
	}
}
