package com.kh.spring.board.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
// @AllArgsConstructor
// 보통 기본 생성자로 생성 후 setter로 넘겨 주는 꼴이기 때문에 모든 필드를 매개변수로 가지는 생성자는 만들지 않아 볼 것
@Setter
@Getter
@ToString
public class Reply {
	
	private int replyNo; 		 //	REPLY_NO	NUMBER
	private String replyContent; //	REPLY_CONTENT	VARCHAR2(400 BYTE)
	private int refBoardNo; 	 //	REF_BNO	NUMBER
	private String replyWriter;  //	REPLY_WRITER	VARCHAR2(30 BYTE)
	private String createDate;   //	CREATE_DATE	DATE
	private String status; 		 //	STATUS	VARCHAR2(1 BYTE)

}