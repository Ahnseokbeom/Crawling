package Saramin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Calendar {
	public static void main(String[] args) throws IOException{
		Document doc = Jsoup.connect("https://www.saramin.co.kr/zf_user/calendar").get();
		Elements movieDetail = doc.select("div.day more-data");
		System.out.println(movieDetail.html());
		String[] detail = new String[19];

		
	}
}
