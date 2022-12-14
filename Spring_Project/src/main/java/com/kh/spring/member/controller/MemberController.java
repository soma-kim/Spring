package com.kh.spring.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	// 비밀번호 암호화를 위한 변수
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*
	 * *Controller 클래스 내부에 메소드를 만들어서 요청에 대한 처리 내용을 작성
	 * => 접근제한자는 무조건 public으로 지정함
	 * => 메소드 상단에 url 매핑값을 지정해야 함
	 * => 메소드명은 의미를 부여해서 적당히 지어야 함
	 * => 매개변수는 있어도 되고, 없어도 됨 (단, 매개변수가 있는 경우 DispatcherServlet이 알아서 값을 전달해 줄 것)
	 * => 리턴 타입은 String 타입으로 응답페이지를 지정할 수 있고, ModelAndView 타입으로도 지정 가능함
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
	
	/*
	 * * Spring에서의 요청 처리 후 응답 데이터를 담고 응답 페이지로 포워딩 또는 url 재요청 하는 방법
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * => Model 객체는 기존의 requestScope와 같은 역할 (요청에 대한 응답 페이지만 사용할 수 있는 scope)
	 * => 포워딩할 응답 뷰로 전달하고자 하는 데이터를 key-value로 담을 수 있음
	 * => 데이터를 담은 후 응답 페이지를 포워딩 하려면 해당 응답페이지 파일명을 "문자열로 리턴" 해 주면 됨
	 * 
	 * [표현법]
	 * 기존의 requestScope에 담기: request.setAttribute("키", 밸류);
	 * Spring의 Model 객체에 값 담기: model.addAttribute("키", 밸류);
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		
		// System.out.println("userId: " + m.getUserId());
		// System.out.println(("userPwd: " + m.getUserPwd()));
		
		// Service단에 m을 전달하면서 요청
		
		// 기존 프로그래밍 방식 => MemberService 객체를 직접 생성해서 호출
		// MemberService mService = new MemberServiceImpl();
		// mService.loginMember(m);
		
		// 스프링 방식 => MemberServiceImpl 클래스도 bean으로 등록
		// 			   스프링에 의해 객체가 생성되게끔
		Member loginUser = memberService.loginMember(m);
		
		// 요청 결과에 따른 응답 화면 처리
		if(loginUser == null) { // 로그인 실패
			
			// 에러 문구를 응답 페이지에 실어서 포워딩
			model.addAttribute("errorMsg", "로그인 실패");
			
			// * 포워딩 방식: url 주소는 그대로고 화면만 바꿔치기 해 주는 개념
			// => 문자열을 리턴 할 때 파일 경로를 포함한 파일명을 제시
			
			// return "/WEB-INF/views/common/errorPage.jsp";
			// 이 메소드를 호출했던 DispatcherServlet에게 포워딩할 경로값만 문자열로 리턴
			// => DispatcherServlert에서는 받은 경로값을 가지고 request.getReqeustDispatcher(리턴값).forward() 구문을 실행할 것임
			
			// 현재 404 오류 뜸!
			//=> /WEB-INF/views/WEB-INF/views/common/errorPage.jsp.jsp
			
			// * Spring 포워딩 시 주의할 점
			// => servlet-context.xml의 주소의 자동 완성을 도와주는 bean 설정에 의해
			// 접두어: "/WEB-INF/views/"
			// 접미어: ".jsp"
			// 를 제외한 나머지 부분에 대한 경로만 문자열로 리턴해야 함!
			
			// 내가 리턴하고 싶은 경로: /WEB-INF/views/common/errorPage.jsp
			// 접두어와 접미어 제외: common/errorPage
			return "common/errorPage";
			
		} else { // 로그인 성공
			
			// loginUser를 sessionScope에 담고 메인 페이지로 url 재요청
			session.setAttribute("loginUser", loginUser);
			
			 // * url 재요청 방식: 주소창에 url을 요청하는 방식
			 // => 문자열을 리턴할 때 redirect: 을 붙여서 주소값을 제시
			 // 예) redirect:요청할url주소
			 // 
			 // 내부적으로 리턴값을 받아 주는 DispatcherServlet이
			 // 해당 요청할 url 주소를 받아 response.sendRedirect(리턴값) 메소드를 호출하는 원리
			
			return "redirect:/";
			// redirect:뒤에 붙는 슬래시(/)가 의미하는 바: contextPath 뒤에 붙는 /를 의미함(== 메인페이지)
		}
		
	}
	*/
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 사용하는 방법
	 * => Model은 데이터를 키-밸류 세트로 담을 수 있는 requestScope와 같은 공간이라고 한다면
	 *    View는 응답뷰에 대한 정보를 담을 수 있는 공간임 
	 * => ModelAndView 객체는 Model 객체와 View 객체를 결합한 형태
	 * (단, Model 객체는 단독 존재가 가능하나 View 객체는 그럴 수 없음)
	 * 
	 * [표현법]
	 * ModelAndView 객체에 데이터를 담고자 한다면: mv.addObject("키", 밸류값);
	 * ModelAndView 객체에 응답뷰에 대한 데이터를 담고자 한다면: mv.setViewName("응답뷰경로또는url주소");
	 */
	
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, ModelAndView mv,
									HttpSession session, String saveId,
									HttpServletResponse response) {
		// 쿠키를 응답데이터로 넘기기 위해 HttpServletResponse까지 매개변수로 받음
		
		if(saveId != null && saveId.equals("y")) {
			// 요청 시 전달값 중에 saveId가 y라면 saveId라는 키값으로 현재 아이디값을 쿠키 생성
			// 새로 만든 쿠키의 saveId라는 키값에 m.getUserId()로 받아온 값을 밸류로 넣겠다
			Cookie cookie = new Cookie("saveId", m.getUserId());
			cookie.setMaxAge(24 * 60 * 60 * 1); // 1초 단위, 유효기간 1일
			response.addCookie(cookie);
			
		} else {
			// 요청 시 전달값 중에 saveId가 y가 아니라면 쿠키 삭제
			// 쿠키는 삭제 구문이 없으나 같은 키값으로 유효기간을 0일로 지정해서 덮어씌우면 삭제 효과를 볼 수 있음!
			Cookie cookie = new Cookie("saveId", m.getUserId());
			cookie.setMaxAge(0); // 쿠키 삭제와 같은 의미
			response.addCookie(cookie);

		}
		
		
		// 암호화 작업 전 로직
		/*
		Member loginUser = memberService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패
			
			// model.addAttribute("errorMsg", "로그인 실패");
			mv.addObject("errorPage", "로그인 실패");
			
			// return "common/errorPage";
			mv.setViewName("common/errorPage"); // 포워딩
			// ModelAndView 객체를 이용해서 응답페이지를 지정할 경우 또한 ViewResolver에 의해 접두어와 접미어를 생략한 형태로 지정해야만 함
			
		} else { // 로그인 성공
			
			session.setAttribute("loginUser", loginUser);
			
			// return "redirect:/";
			mv.setViewName("redirect:/"); // url 재요청방식
			
		}
		
		// 어느 경우에나 mv를 리턴하기 때문에 따로 2번 적을 필요 없이 조건문 밖에 기술해도 됨!
		return mv;
		*/
		
		// 암호화 작업 후 로직
		// => BCrypt 방식에 의해 복호화가 불가능한 암호문 형태의 비밀번호와 일치하는지 대조 작업
		// Member m의 userId 필드: 사용자가 입력한 아이디 (평문)
		//           userPwd 필드: 사용자가 입력한 비밀번호 (평문)
		Member loginUser = memberService.loginMember(m); // 그대로 호출
		
		// loginUser: 오로지 아이디만 가지고 조회된 회원의 정보
		// Member loginUser의 userPwd 필드: DB에 기록된 암호화된 비밀번호
		// 일치하는 아이디 기준으로 모든 컬럼을 가지고 왔으므로 현재 DB에서 가지고 온 정보에는 "암호화된 비밀번호"가 들어 있음
		
		// BCryptPasswordEncoder 객체의 matches 메소드
		// matches(평문, 암호문) 을 작성하면 내부적으로 평문과 암호문을 맞추는 작업이 이루어짐
		// 두 구문이 일치하는지 비교 후 일치하면 true 반환
		if(loginUser != null &&
				bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			
			// 비밀번호도 일치한다면 => 로그인 성공
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", "로그인에 성공했습니다.");
						
			mv.setViewName("redirect:/");
			
		} else { // 일치하지 않는다면 => 로그인 실패
			
			mv.addObject("errorMsg", "로그인 실패");
			
			// /WEB-INF/views/common/errorPage.jsp
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		
		// 세션 무효화 메소드: session.invalidate();
		session.invalidate();
		
		// 메인 페이지로 url 요청
		// mv.setViewName("redirect:/"); // 반환형 수정, ModelAndView 매개변수 요청 후 이렇게 해도 무방!
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		
		// 회원가입 페이지를 포워딩
		// 포워딩 하고자 하는 경로: /WEB-INF/views/member/memberEnrollForm.jsp
		// 접두어와 접미어를 제외한 경로: member/memberEnrollForm
		return "member/memberEnrollForm";
		
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		
		System.out.println(m);
		
		// 1. 한글 깨짐 문제 발생 => 요청 시 전달값을 뽑기 전에 먼저 UTF-8 형식으로 인코딩 설정하기
		// 이 시점에서 인코딩 설정을 한다면?
		// 해결책: 요청이 DispatcherServlet에 도달하기 전에 "인코딩 필터"를 거쳐가게끔 해 줄 것
		//       스프링에서 제공하는 인코딩 필터를 web.xml에 등록(제일 먼저 읽히니까)
		
		// 2. 요청 시 나이를 입력하지 않았을 경우 => int 자료형인 age라는 필드에
		//								빈 문자열이 넘어와 
		// 400 에러(Bad Request Error) : 형식에 맞지 않는 데이터가 넘어온 경우 발생함
		// 해결책: Member 클래스의 age 필드를 int형에서 String 형으로 변경
		
		// 3. 현재 비밀번호가 사용자가 입력한 그대로의 평문임
		// 사람이 그냥 눈으로 의미를 파악할 수 있는 데이터: 평문
		// 사람이 그냥 눈으로 의미를 파악할 수 없게 처리한 데이터: 암호문
		// => 스프링에서 제공하는 Bcrypt 방식으로 비밀번호를 암호화 할 것
		// 1) 스프링 시큐리티 모듈에서 제공하는 라이브러리를 추가 (Maven)
		// 2) BCryptPasswordEncoder 클래스를 xml 파일에 bean으로 등록
		// 3) web.xml에 spring-security.xml 파일을 로딩할 수 있게 등록
		
		// System.out.println("평문: " + m.getUserPwd());
		
		// 암호화 작업 (암호문을 만들어 내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		// System.out.println("암호문: " + encPwd);
		// => 같은 평문이어도 매번 다른 암호문 결과가 나옴
		// => 평문 + salt(랜덤값) => 암호화 작업이 이루어지기 때문
		
		// Member 객체의 userPwd 필드의 값을 암호문으로 바꿔치기 => setter 메소드
		m.setUserPwd(encPwd);
		
		int result = memberService.insertMember(m);
		
		if(result > 0) { // 성공, 메인페이지 url 재요청
			
			// 일회성 알람 문구
			session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
			return "redirect:/";
			
		} else { // 실패, 에러 문구를 담아서 에러 페이지 포워딩
			
			model.addAttribute("errorMsg", "회원가입 실패");
			
			// /WEB-INF/views/common/errorPage.jsp
			return "common/errorPage";
			
		}
		
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		
		// /WEB-INF/views/member/myPage.jsp
		return "member/myPage";
		
	}
	
	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {
		
		// System.out.println(m);
		int result = memberService.updateMember(m);
		
		if(result > 0) { // 성공
			
			// 수정 성공일 경우 DB로부터 수정된 회원의 정보를 다시 조회해서
			// session에 loginUser 키값으로 덮어씌워야 함
			// => 이때, 기존의 loginMember 메소드를 재활용해서 조회
			Member updateMem = memberService.loginMember(m);
			session.setAttribute("loginUser", updateMem);
			
			// session에 일회성 알람 문구도 담기
			session.setAttribute("alertMsg", "성공적으로 회원 정보가 변경되었습니다.");
			
			// 마이페이지 url 재요청
			return "redirect:/myPage.me";
			
		} else { // 실패
			
			model.addAttribute("errorMsg", "회원 정보 변경 실패");
			
			// /WEB-INF/views/common/errorPage.jsp
			return "common/errorPage";
			
		}
		
	}
	
	@RequestMapping("delete.me")
	public String deleteMember(String userPwd, String userId, HttpSession session, Model model) {
		
		// userPwd: 회원 탈퇴 요청 시 사용자가 입력했던 평문 비밀번호
		// session의 loginUser Member 객체의 userPwd 필드: 현재 이 로그인한 회원의 암호화된 비밀번호
		// => 이 두 가지 정보가 있어야만 matches 메소드 활용 가능!
		
		String encPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		
		// 비밀번호 대조 작업
		if(bcryptPasswordEncoder.matches(userPwd, encPwd)) {
			
			// 비밀번호가 맞을 경우 => 탈퇴 처리
			int result = memberService.deleteMember(userId);
			
			if(result > 0) { // 탈퇴 처리 성공
				
				// 로그아웃 처리 후 일회성 알람 메시지 담기, 메인 페이지로 url 재요청
				// session.invalidate(); // 로그아웃 처리이나 사용 불가! 일회성 alert창을 띄워야 하기 때문
				session.removeAttribute("loginUser"); // 로그인한 회원의 정보만 지워 줌
				session.setAttribute("alertMsg", "성공적으로 탈퇴되었습니다. 그동안 이용해 주셔서 감사합니다.");
				
				return "redirect:/"; // 메인페이지로 url 재요청
				
			} else { // 탈퇴 처리 실패 => 에러 문구를 담아서 에러 페이지로 포워딩
				
				model.addAttribute("errorMsg", "회원 탈퇴 실패");
				
				// /WEB-INF/views/common/errorPage.jsp
				return "common/erroePage";
				
			}
			
		} else { // 비밀번호가 틀릴 경우 => 비밀번호 틀렸다고 알려 주고 마이페이지 url 재요청
			
			session.setAttribute("alertMsg", "비밀번호를 잘못 입력하였습니다. 확인해 주세요.");
			return "redirect:/myPage.me";
			
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="idCheck.me", produces="text/html; charset=UTF-8")
	public String idCheck(String checkId) {
		
		// System.out.println(checkId);
		
		int count = memberService.idCheck(checkId);
		
		/*
		if(count > 0) { // 이미 존재하는 아이디 => 사용 불가능 (NNNNN)
			
			return "NNNNN";
			
		} else { // 사용 가능 (NNNNY)
			
			return "NNNNY";
			
		}
		*/
		
		// 위와 같이 if문을 써도 무방하나 삼항연산자로도 표현 가능함!
		return (count > 0) ? "NNNNN" : "NNNNY";
		
	}
	
}