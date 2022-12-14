package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 * - 자동 코드 생성 라이브러리, 코드 다이어트 라이브러리
 * - 필드마다 반복되는 getter, setter, toString 등의 메소드 작성 코드를 생략할 수 있게끔 도와줌
 *   (단, 필드는 정확하게 다 정의해야 함)
 *   
 * * Lombok 설치 방법
 * 1. 라이브러리 jar 파일을 다운로드 후 프로젝트에 적용(Maven pom.xml)
 * 2. 다운로드 된 jar 파일을 찾아서 실행 (작업할 IDE를 선택해서 설치)
 * 3. IDE 재실행
 * 
 * * Lombok 사용 시 주의사항
 * => uName, bTitle와 같은 앞글자가 외자인 필드명은 만들지 말 것!
 * - userId 필드 -> setUserId() / getUserId()
 * - uId 필드        -> setuName() / getuName()
 * => 협업 시 팀원들 중 롬복을 사용하지 못하는 사람이 1명이라도 있다면 롬복 사용 불가함
 * 
 */

@NoArgsConstructor // 기본생성자를 만들어 주는 어노테이션
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자를 만들어 주는 어노테이션
@Setter // setter 메소드들
@Getter // getter 메소드들
@ToString // toString 메소드
public class Member {
	
	private String userId; 	 //	USER_ID	VARCHAR2(30 BYTE)
	private String userPwd;  //	USER_PWD	VARCHAR2(100 BYTE)
	private String userName; //	USER_NAME	VARCHAR2(15 BYTE)
	private String email; 	 //	EMAIL	VARCHAR2(100 BYTE)
	private String gender; 	 //	GENDER	VARCHAR2(1 BYTE)
	// private int age; 	 //	AGE	NUMBER
	private String age; 	 // 400 에러 해결방법
	private String phone; 	 //	PHONE	VARCHAR2(13 BYTE)
	private String address;  //	ADDRESS	VARCHAR2(100 BYTE)
	private Date enrollDate; //	ENROLL_DATE	DATE
	private Date modifyDate; //	MODIFY_DATE	DATE
	private String status;   //	STATUS	VARCHAR2(1 BYTE)
	


}