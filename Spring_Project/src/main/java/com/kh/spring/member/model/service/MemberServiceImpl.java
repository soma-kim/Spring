package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		return loginUser;
	}

	@Override
	public int insertMember(Member m) {
		return 0;
	}

	@Override
	public int updateMember(Member m) {
		return 0;
	}

	@Override
	public int deleteMember(Member m) {
		return 0;
	}

	@Override
	public int idCheck(String checkId) {
		return 0;
	}

}
