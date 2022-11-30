package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	
	public static final String SERVICEKEY = "Tx1KtZT26sjtr%2F1tn0i0ea5161fXk0m8t0J7eWdWJsk1GBVTcGj%2B8zDIsbwQ8pibb461SlMf6mZ4LTTSqkUNyw%3D%3D";
	
	@ResponseBody
	// @RequestMapping(value="air.do", produces="application/json; charset=UTF-8") // json으로 응답데이터를 넘기고 싶음
	@RequestMapping(value="air.do", produces="text/xml; charset=UTF-8") // xml로 응답데이터를 넘기고 싶을 때
	public String airPollution(String location) throws IOException { // 부모 타입인 IOException으로 바꿔 줬음
		
		// System.out.println(location);

		 String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		 		url += "?serviceKey=" + SERVICEKEY;
		 		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		 		// url += "&returnType=json"; // json으로 요청 결과를 받고 싶을 때
		 		url += "&returnType=xml"; 
		 		url += "&numOfRows=50"; // 결과의 개수
		 		
		 URL requestUrl = new URL(url); // java.net.URL로 import
		 
		 HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		 
		 urlConnection.setRequestMethod("GET");
		 
		 BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		 
		 String responseText = "";
		 String line; // null
		 
		 while((line = br.readLine()) != null) {
			 
			 responseText += line;
			 
		 }

		 // 응답 데이터를 보내 주고자 한다면 굳이 ArrayList로 파싱할 필요가 없음!
		 // String을 리턴하되, @ResponseBody 추가, produces 설정 추가!
		 
		 br.close();
		 urlConnection.disconnect();
		 
		 return responseText;
		
	}

}
