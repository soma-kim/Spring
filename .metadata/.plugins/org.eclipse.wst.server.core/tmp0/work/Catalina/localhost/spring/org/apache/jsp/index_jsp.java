/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.82
 * Generated at: 2022-11-17 02:39:07 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("	<!-- \r\n");
      out.write("		* Spring framework의 특징\r\n");
      out.write("		1. IOC(Inversion Of Control)\r\n");
      out.write("		제어의 역전 => 객체들은 관리할 권한이 개발자가 아닌 Spring에게 있음\r\n");
      out.write("					내가 직접적으로 new 구문을 이용해서 객체를 생성할 일이 거의 없음\r\n");
      out.write("		\r\n");
      out.write("		2. DI (Dependency Injection)\r\n");
      out.write("		의존성 주입 => 객체간의 의존관계를 알아서 설정해 줌\r\n");
      out.write("					예) MVC 패턴에서 Controller -> Service 객체 생성 후 메소드 호출 -> Dao 객체 생성 후 메소드 호출\r\n");
      out.write("					   JDBC에서 DriverManager -> Connection 객체 생성 - > PreparedStatement 객체 생성\r\n");
      out.write("		\r\n");
      out.write("		3. Spring AOP (Aspect Oriented Programming)\r\n");
      out.write("		관점 지향 프로그래밍 => 객체 지향 프로그래밍을 보완한 개념\r\n");
      out.write("						객체 지향에서도 부족했던 재활용성, 코드의 중복을 더 줄여서 모듈화하게 도와주는 기법 \r\n");
      out.write("						\r\n");
      out.write("		--------------- 1~3번의 특징까지가 진짜 중요한 개념임 -------------------\r\n");
      out.write("		\r\n");
      out.write("		4. POJO (Plain Old Java Object)\r\n");
      out.write("		내가 직접 만든 클래스를 지칭 => POJO Class\r\n");
      out.write("							  다른 프레임워크들은 대부분 내가 직접 만든 클래스를 혼용할 수 없게 되어 있는데\r\n");
      out.write("							 Spring이나 MyBatis는 내가 직접 만든 클래스를 혼용해서 쓸 수 있음\r\n");
      out.write("		\r\n");
      out.write("		5. Spring JDBC\r\n");
      out.write("		영속성 작업과 관련된 JDBC도 사용할 수 있고, MyBatis와 같은 영속성 프레임워크와의 연동도 지원함\r\n");
      out.write("		\r\n");
      out.write("		6. Spring MVC\r\n");
      out.write("		MVC 패턴으로 코딩하기 위한 Model, View, Controller간의 관계를 알아서 IOC, DI를 통해 관리해 줌\r\n");
      out.write("		\r\n");
      out.write("		7. PSA(Portable Service Abstraction)\r\n");
      out.write("		스프링에서는 수 많은 모듈들을 붙여 쓸 수 있는데 일일이 연동해 주는 것이 아니라 모듈만 바꿔치기 해서 사용할 수 있게끔 도와줌\r\n");
      out.write("	 -->\r\n");
      out.write("	 \r\n");
      out.write("	 여기는 index.jsp야\r\n");
      out.write("	 \r\n");
      out.write("	 ");
      if (true) {
        _jspx_page_context.forward("WEB-INF/views/main.jsp");
        return;
      }
      out.write("\r\n");
      out.write("	 \r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
