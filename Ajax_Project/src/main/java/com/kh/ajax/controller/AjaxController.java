package com.kh.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
