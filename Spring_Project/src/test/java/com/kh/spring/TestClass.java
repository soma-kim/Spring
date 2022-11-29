package com.kh.spring;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j; // Lombok이 제공하는 어노테이션임을 알 수 있음

// 이 클래스에서 Logging을 하고 싶을 때 자동으로 전역변수로써 Logger 객체가 생성됨(이때, 객체명은 log)
@Slf4j
public class TestClass {
	
	/*
	 * * 애플리케이션 테스트
	 * 1. 단위 테스트 : 개발하는 과정에서 이루어지는 메소드 단위의 코드가 잘 작동되는지 테스트
	 * 2. 통합 테스트 : 모든 코드를 취합한 후에 모든 기능들이 제대로 잘 작동되는지 테스트하는 개념
	 * 3. 시스템 테스트 : 모든 코드들을 취합 후 비기능적 요소들이 제대로 잘 작동되는지 테스트하는 개념
	 * 				(사용상의 편의, 보안적 측면, 요구사항 반영, ...) 
	 * 4. 인수 테스트 : 실제 사용자의 입장에서 테스트를 수행하는 개념
	 * 				QA 업무 (테스트 케이스를 나열 후 검사하는 업무)
	 * 
	 * * JUnit
	 * - 단위 테스트 기능을 제공하는 프레임워크
	 * - @Test 어노테이션을 이용함(일종의 main 메소드를 만들어 주는 효과를 줌)
	 */
	
	@Test // 이 어노테이션을 붙이는 순간 test 메소드가 main method인 것처럼 동작함
	public void test() {
		
		// System.out.println("잘 호출되나?");
		
		/*
		// 로깅 수행하는 도구 생성 (Logger 타입)
		Logger logger = LoggerFactory.getLogger(TestClass.class);
		
		// 로깅 수행 시 필요한 출력문들
		logger.debug("난 debug야");
		logger.info("난 info야");
		logger.warn("난 warn이야");
		logger.error("난 error야");
		// => 일단 이 메시지의 종류와 어느 위치에서 출력됐는지 경로를 보여 줌
		//    그 뒤에 로그를 남기고자 하는 메시지 내용이 출력됨을 알 수 있음
		// => debug 출력문은 출력이 되지 않음을 볼 수 있음
		// src/test/resources/log4j.xml
		*/
		
		log.debug("난 debug야");
		log.info("난 info야");
		log.warn("난 warn이야");
		log.error("난 error야");
		// 개발 중 디버깅 할 일이 있다면 System.out.println 대신에 log.debug로 출력해 보기!
		
	}

}
