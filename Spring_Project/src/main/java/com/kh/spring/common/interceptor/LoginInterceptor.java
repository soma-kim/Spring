package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * * Interceptor
 * - 해당 Controlelr가 실행되기 전 혹은 실행된 후에 요청을 낚아채서 실행할 내용을 작성 가능
 * - 주로 로그인 유무 판단, 로그인한 회원의 권한 또는 직급 체크 등에 사용됨
 * 
 * * Interceptor와 Filter의 차이점
 * - Filter : Servlet에서 발생한 개념
 *            요청이 Servlet에 도달하기 전에 가로채서 선처리하는 시점
 * - Interceptor : Spring에서 발생한 개념
 *                 요청이 Controller의 메소드에 도달하기 전에 가로채서 선처리 또는 후처리 하는 시점
 *                 
 * * 요청의 흐름
 * 사용자 -> View -> "Filter" -> DispatcherServlet -> "Interceptor" -> Controller
 * 
 * * Interceptor 클래스 작성 방법
 * - HanlderInterceptorAdapter 클래스를 상속받아 구현
 * - Interceptor 메소드 종류 (오버라이딩하여 사용)
 *   1) preHandle (선처리 구문 작성) : DispatcherServlet에서 컨트롤러를 호출하기 전에 낚아채는 영역
 *   2) postHandle (후처리 구문 작성) : 컨트롤러에서 요청 처리 후 DispatcherServlet으로 뷰 정보가 리턴되는 순간에 낚아채는 영역
 *   
 *   - 만들어진 Interceptor 클래스는 요청 전 해당 Interceptor를 거쳐갈 수 있게끔 servelt-context.xml 파일에 등록함
 * 
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

	// 선처리용 preHandle 메소드 오버라이딩
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// boolean 리턴 => true / false
		// true 리턴했을 시 : 기존 요청 흐름대로 해당 Controller가 제대로 실행
		// false 리턴했을 시: Controller가 실행되지 않음
		
		// 로그인 유무 검사를 위한 Session 객체 얻어내기
		HttpSession session = request.getSession();
		
		// loginUser 키값이 있나 검사
		if(session.getAttribute("loginUser") != null) {
			// 현재 요청을 보낸 사람이 로그인되어 있는 상태라면
			// => Controller가 실행
			return true;
		} else {
			// 현재 요청을 보낸 사람이 로그인이 되어 있지 않은 상태라면
			// => Controller 실행 막기
			
			// alertMsg로 "로그인 한 회원만 이용 가능하다"는 알람 메시지 띄우기
			session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스입니다.");
			
			// 응답 뷰 지정(메인페이지로 url 재요청)
			response.sendRedirect(request.getContextPath());
			
			return false;
		}
		
	}
	
}
