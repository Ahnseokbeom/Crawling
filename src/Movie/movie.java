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

public class movie {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/").get();
		Elements rank = doc.select("div.sect-movie-chart strong.rank");
		Elements movieDetail = doc.select("div.box-contents a");
		Elements movieImg = doc.select("div.sect-movie-chart a span.thumb-image img");
		Elements movieScore = doc.select("div.score strong span");
		Elements movieRelease = doc.select("span.txt-info strong");
		Elements movieRate = doc.select("span.percent");
		Elements movieTitle = doc.select("strong.title");
		String[] detail = new String[19];
		String[] img = new String[19];
		String[] score = new String[19];
		String[] release = new String[19];
		String[] rate = new String[19];
		String[] title = new String[19];
		String[] grade = new String[19];
		
		int i = 0;
		for(Element e : movieDetail) {
			if(e.attr("href").charAt(1)=='m') {
			detail[i++] = "http://www.cgv.co.kr"+e.attr("href");
			}
		}
		for(i = 0;i<19;i++) grade[i] = rank.get(i).text();
		for(i = 0;i<19;i++) img[i] =  movieImg.get(i).attr("src");
		for(i = 0;i<19;i++) score[i] = movieScore.get(i).text();
		for(i = 0;i<19;i++) release[i] = movieRelease.get(i).text();
		for(i = 0;i<19;i++) rate[i] = movieRate.get(i).text().replaceAll("[^0-9]","");
		for(i = 0;i<19;i++) title[i] = movieTitle.get(i).text();
		System.out.println(rate[15].equals(""));
		try {
			String titles = "";
			String grades = "";
			String releases = "";
			int rates = 0;
			String imgs = "";
			String details = "";
			String sql = "";
			java.sql.Statement st = null;
//			ResultSet rs = null;
			Connection con = null;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
					"root", "4612");
			st = con.createStatement();
			st.executeUpdate("use movie;");
			for(i = 0;i<19;i++) {
				titles = title[i];
				grades = grade[i];
				releases = release[i];
				if(rate[i].equals("")) rates = 0;
				else {
					rates = Integer.parseInt(rate[i]);
				}
				imgs = img[i];
				details = detail[i];
//				sql = "insert into Game values(?,?,?,?,?)";
				sql = "insert into movie values(?,?,?,?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(sql);
				System.out.println(i+1);
				pst.setInt(1, i+1);
				pst.setString(2, titles);
				pst.setString(3, grades);
				pst.setString(4, releases);
				pst.setInt(5, rates);
				pst.setString(6, imgs);
				pst.setString(7, details);
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
