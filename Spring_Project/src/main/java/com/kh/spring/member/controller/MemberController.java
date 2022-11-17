package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

/*
 * *Spring에서는 내부적으로 중앙 요청 처리 서블릿(DispatcherServlet)이 존재함
 * => 모든 요청은 DispatcherServlet에 의해 분배되는 구조
 * => 기존의 방식처럼 매 요청마다 직접적으로 Servlet을 만들 필요가 없음 (일반 클래스로 Controller 만들기)
 * 
 * *DispatcherServlet에 의해 MemberController 클래스의 어느 메소드가 호출
 * => MemberController 객체가 필요함
 * => Spring이 MemberController 객체를 만들 수 있게끔 bean 등록을 해 줘야 함!
 */

@Controller // Controller 타입의 어노테이션을 붙여 주면 빈 스캐닝을 통해 자동으로 bean으로 등록: 가독성 때문에 Component보다는 Controller를 권장
// @Component // 타입에 상관없이 bean으로 등록
public class MemberController {
	
	// 기존 방식
	// private MemberService memberService = new MemberServiceImpl();
	/*
	 * * 기존 객체 생성 방식(new 구문 사용하여 객체 생성)
	 * 객체 간의 결합도가 높아짐 (소스코드가 수정될 경우 일일이 다 바꾸어 주어야 함)
	 * 서비스가 동시에 매우 많은 횟수가 요청될 경우 그만큼 객체가 생성됨
	 * 
	 * * Spring의 DI(Dependency Injection)라는 특성을 활용한 방식
	 * 필요할 때마다 Spring에 의해서 객체가 만들어진 후 주입시켜 줌 (내가 직접 생성하는 것이 아니기 때문에 결합도를 낮춰 줌)
	 * => new라는 키워드 없이 선언문만 써 줘도 되지만 선언 구문 위에 @Autowired라는 어노테이션을 반드시 붙여야 함!
	 */
	
	// 스프링 방식
	@Autowired // @Autowired 어노테이션 기재해 주고
	private MemberService memberService; // 선언만 해 주면 됨!
	
	/*
	 * *Controller 클래스 내부에 메소드를 만들어서 요청에 대한 처리 내용을 작성
	 * => 접근제한자는 무조건 public으로 지정함
	 * => 메소드 상단에 url 매핑값을 지정해야 함
	 * => 메소드명은 의미를 부여해서 적당히 지어야 함
	 * => 매개변수는 있어도 되고, 없어도 됨 (단, 매개변수가 있는 경우 DispatcherServlet이 알아서 값을 전달해 줄 것)
	 * => *** 
	 * 
	 */
	
	/*
	@RequestMapping(value="login.me")
	public void loginMember() {
		System.out.println("잘 실행되나");
	}
	*/
	
	/*
	 * * Spring에서 파라미터 (요청 시 전달값)를 받는 방법
	 * 
	 * 1. HttpServletRequest 객체를 이용해서 전달받기 (기존의 jsp / servlet 방식)
	 * => 해당 메소드의 매개변수로 HttpServletRequest 객체를 전달받아야 함
	 * => 해당 메소드 호출 시 자동으로 Spring에 의해 객체가 생성돼서 매개변수로 주입해 줌
	 */
	/*
	@RequestMapping(value="login.me")
	public void loginMember(HttpServletRequest request) {
	// HttpServletRequest request는 스프링이 자동으로 생성해서 나에게 줌
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		System.out.println("userId: " + userId);
		System.out.println("userPwd: " + userPwd);
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * => request.getParameter("키")로 밸류를 뽑아 오는 역할을 대신해 주는 어노테이션
	 * => 어노테이션의 value 속성으로 jsp에서 작성했던 name 속성값을 담으면 알아서 해당 매개변수로 요청 시 전달값을 받아 옴
	 * => 만약, 넘어온 값이 비어 있는 형태라면 defaultValue 속성으로 기본값 지정 가능
	 */
	/*
	@RequestMapping(value="login.me")
	public void loginMember(@RequestParam(value="userId", defaultValue="없음") String userId
						  , @RequestParam(value="userPwd") String userPwd) {
			
		System.out.println("userId: " + userId);
		System.out.println("userPwd: " + userPwd);
		
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * => 단, 매개변수명을 jsp의 name 속성값과 동일하게 세팅해 둬야 자동으로 값이 주입됨
	 * => 또한, 위에서 썼던 defaultValue 같은 추가적인 속성을 사용할 수 없음
	 * => name 속성값이 매개변수명과 일치하지 않는다면 null 값 뜸!
	 * 
	 */
	/*
	@RequestMapping("login.me") // 어노테이션 내의 키-밸류 세트가 1개라면 "value=" 생략 가능
	public void loginMember(String userId, String userPwd) {
		
		// System.out.println("userId: " + userId);
		// System.out.println("userPwd: " + userPwd);
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
		// Service 단에 m을 전달하면서 조회
		
		// 결과에 따른 응답화면을 지정
		
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식
	 * => 해당 메소드의 매개변수로 요청 시 전달값을 담고자 하는 VO 클래스 타입을 세팅 후
	 *    요청 시 전달값의 키값(name 속성값)과 VO 클래스의 정의되어 있는 필드명을 일치시켜 주면
	 *    Spring이 내부적으로 해당 전달값을 뽑아 VO 객체로 가공해 넘겨 주는 방식
	 * => 반드시 jsp의 name 속성값과 담고자 하는 필드명이 동일해야 함
	 * 
	 * * 이 방식의 원리
	 * 1) Spring에 의해 내부적으로 요청 시 전달값이 뽑힘
	 * 2) Spring에 의해 내부적으로 "기본생성자"를 통해 VO 객체가 생성됨
	 * 3) Spring에 의해 요청 시 name 속성값과 일치하는 해당 VO의 필드를 찾아 setter 메소드로 가공
	 */
	
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		
		// System.out.println("userId: " + m.getUserId());
		// System.out.println(("userPwd: " + m.getUserPwd()));
		
		// Service단에 m을 전달하면서 요청
		
		// 기존 프로그래밍 방식 => MemberService 객체를 직접 생성해서 호출
		// MemberService mService = new MemberServiceImpl();
		// mService.loginMember(m);
		
		// 스프링 방식 => MemberServiceImpl 클래스도 bean으로 등록
		// 			   스프링에 의해 객체가 생성되게끔
		Member loginUser = memberService.loginMember(m);
		
		// 결과에 따른 응답 화면 조회
		
	}

}