package com.kh.ajax.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {
	
	/*
	 * Spring에서 Ajax 요청에 따른 응답 데이터 넘기는 방법
	 * 1. HttpServletResponse 객체로 응답데이터 응답하기
	 * (기존의 jsp / Servlet 때 썼던 Stream을 이용한 방식)
	 */
	
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		System.out.println(name);
		System.out.println(age);
		
		// 요청 처리를 위해 서비스 - DAO 호출
		
		// 요청 처리가 다 되었다는 가정 하에
		// 요청한 그 페이지에 응답할 데이터가 있을 경우
		String responseData = "응답 문자열: " + name + "님은 " + age + "살입니다.";
		
		// response로 응답 데이터 넘기기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);
		// Servlet 때는 doGet() 메소드 정의 부분에 throws 구문이 추가되어있는데
		// 우리도 유사하게 throws 구문을 추가 => 이 메소드를 호출했던 DispatcherServlet이 처리해줌
	}
	*/
	
	/*
	 * Spring에서 Ajax 요청에 따른 응답 데이터 넘기는 방법
	 * 2. 응답할 데이터를 문자열 타입으로 반환 (HttpServletResponse를 안 써도 됨)
	 * => 단, 문자열을 리턴하면 원래는 포워딩 방식을 의미했었음
	 *    (그냥 문자열을 리턴하면 응답페이지로 인식을 해서 해당 뷰 페이지를 찾을 것: 404 에러 발생)
	 *    
	 * => 따라서 내가 리턴하는 문자열이 응답 페이지가 아닌 응답 데이터임을 선언하는 어노테이션 @RequestBody를 써 줘야 함
	 *    추가적으로 응답 데이터에 한글이 포함될 경우에는 아래와 같이 produces 속성을 설정해 줘야 함
	 * 
	 * 
	 *
	 * 
	 *  내 필기~! 
	 * 반환형을 String으로 바꾸고 String 타입의 변수를 반환하되,
	 * 이것이 응답페이지가 아닌 응답 데이터라는 것을 명시해 주는 @ResponseBody 어노테이션을 써 줘야 함!
	 * 
	 * => ?? ???: ????? 20????.
	 * 이 과정까지 해 줬을 때, 응답 데이터가 잘 넘어오나 인코딩이 깨짐
	 * 이때는 RequestMapping에 produces 속성 추가할 것!
	 * @ResponseBody로 응답데이터를 넘기고자 한다면 RequeestMapping에 produces 속성으로 내가 넘길 값의 데이터 형식을 지정해 줘야 함! 속성이 두 개가 됐으니 value도 생략 불가능 
	 * 
	 */
	
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답 문자열: " + name + "님은 " + age + "살입니다.";
		
		return responseData;
		// 스프링에서 문자열을 리턴하면 곧 DispatcherServlet에 의해 해당 화면이 포워딩됨
		// => prefix + responseData + suffix 해당 파일을 찾아 포워딩 시도가 일어남
		// => "/WEB-INF/views/" + "응답 문자열: ~~~" + ".jsp"를 못 찾기 때문에 404 에러 발생
		
		// 주의할 점: Spring에서 Ajax상의 응답 데이터를 문자열로 리턴하고자 한다면
		//         현재 내가 리턴하는 데이터가 응답 페이지에 대한 정보가 아니라 응답 데이터임을 항상 명시해야 함
		// => 메소드 선언부 상단에 @ResponseBody를 붙여야 함
		// 이때, 응답 데이터에 한글이 있다면 response.setContentType() 메소드에서 지정한 설정
		
	}
	*/
	
	/*
	// 다수의 응답 데이터가 있을 경우
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		// 요청 처리가 다 되었다는 가정 하에 데이터 응답
		// response.setContentType("text/html; charset=UTF-8");
		// response.getWriter().print(name);
		// response.getWriter().print(age);
		// 따로 통로를 열어 보낼 수는 있지만 보내면 한 개의 문자열로 연이어서 보내는 꼴임
		// 홍길동30 => 여러 개의 데이터가 연달아서 응답됨
		
		// JSON(JavaScript Object Natation) 형태로 응답
		// JSONArray => [값, 값, 값, ...] => 자바스크립트의 배열 형식(자바로 따지면 ArrayList)
		
		// 1. JSONArray로 담아서 응답
		// JSONArray jArr = new JSONArray(); // []: 이 시점에 빈 배열 생성됨
		// jArr.add(name); // ["홍길동"]
		// jArr.add(age); // ["홍길동", 30]
		
		// response.setContentType("application/json; charset=UTF-8");
		// response.getWriter().print(jArr);
		
		// 2. JSONObject로 담아서 응답
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("name", name); // {name:"홍길동"}
		jObj.put("age", age); // {name:"홍길동", age:30}
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
		
		
	}
	*/
	
	// 여러 개의 응답 데이터를 넘기는 경우 (response 객체 사용 안 함)
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		// 다수의 응답 데이터가 있다라는 가정 하에 JSONObject 타입으로 넘기기
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("name", name); // {name:"홍길동"}
		jObj.put("age", age); // {name:"홍길동", age:30}
		
		return jObj.toJSONString();
		
	}
	

	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int userNo) {
		
		// DB로부터 조회했다는 가정 하에 Member 객체 생성하기
		Member m = new Member("user01", "pass01", "홍길동", 20, "01011112222");
		/*
		// JSON 형태로 만들어서 응답 (내가 일일이 필드명을 키값으로 지정해 줌)
		JSONObject jObj = new JSONObject();
		jObj.put("userId", m.getUserId());
		jObj.put("userName", m.getUserName());
		jObj.put("age", m.getAge());
		jObj.put("phone", m.getPhone());
		
		return jObj.toJSONString();

	*/
	
	// GSON : Google JSON의 약자
	// => VO 객체를 JSONObject로 가공할 때 내부적으로 필드명을 키값으로 잡아서 가공해 줌
	Gson gson = new Gson();
	return gson.toJson(m); // {userId : "user01", userName : "홍길동", ...}

	}
	
	
	@ResponseBody
	@RequestMapping(value="ajax3.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod3() {

		// DB를 통해 모든 회원의 정보를 조회했다라는 가정하에 ArrayList 생성
		ArrayList<Member> list = new ArrayList<Member>();
								// JDK 8 버전 쓸 때는 문법상 <제네릭> 생략이 가능했으나 6 버전에서는 생략 불가함!
		
		list.add(new Member("user01", "pass01", "홍길동", 20, "01011112222"));
		list.add(new Member("user02", "pass02", "김말똥", 30, "01022223333"));
		list.add(new Member("user03", "pass03", "박개순", 25, "01099998888"));
		list.add(new Member("user04", "pass04", "이말순", 50, "01088887777"));

		return new Gson().toJson(list);
		
	}
}
