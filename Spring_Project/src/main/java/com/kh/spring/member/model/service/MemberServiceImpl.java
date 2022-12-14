package com.kh.spring.member.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

// @Conponent // 도 가능하긴 함!
@Service
public class MemberServiceImpl implements MemberService {

	// Spring에서 MyBatis를 사용하기 위한 SqlSessionTemplate 변수 선언
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// MemberDao 객체 생성 선언문
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public Member loginMember(Member m) {
		
		/*
		 * 기존의 순수 MyBatis 코드
		 * SqlSession sqlSession = Template.getSqlSession();
		 * // 이 시점에서 객체 생성됨
		 * 
		 * Member loginUser = new MemberDao().loginMember(sqlSession, m);
		 * // 이 시점에서 객체 생성됨
		 * 
		 * sqlSession.close();
		 * return loginUser;
		 */
		
		// 순수 MyBatis에서는 SqlSession 객체가 필요
		// 스프링에서 MyBatis를 쓰려면 SqlSessionTemplate 객체가 필요함
		// => root-context.xml에서 bean으로 등록했었음
		// => 내가 직접 new 구문을 통해 객체를 생성할 필요가 없음(@Autowired 어노테이션으로 선언)
		
		Member loginUser = memberDao.loginMember(sqlSession, m);
		
		// SqlSessionTemplate 객체를 bean으로 등록 후 @Autowired 해 줌으로써
		// Spring이 해당 객체를 생성해 사용 후 자동으로 객체를 반납함
		// 내가 직접 close 메소드로 자원 반납할 필요가 없어짐!
		
		return loginUser;
	}

	@Override
	public int insertMember(Member m) {
		
		return memberDao.insertMember(sqlSession, m);
		
	}

	@Override
	public int updateMember(Member m) {
		
		return memberDao.updateMember(sqlSession, m);
		
	}

	@Override
	public int deleteMember(String userId) {
		
		return memberDao.deleteMember(sqlSession, userId);
	}

	@Override
	public int idCheck(String checkId) {
		
		return memberDao.idCheck(sqlSession, checkId);
		
	}

}
