package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository // Dao 역할을 하는 bean으로 등록하겠다!
// 주로 DB(저장소)와 관련된 작업 (== 영속성 작업)을 처리하겠다라는 뜻
public class MemberDao {
	
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		
		/*
		 * SQL문 종류에 따른 메소드를 호출
		 * => SqlSessio 객체와 SqlSessionTemplate 객체에서 제공하는 메소드 종류가 동일
		 */
		
		// 로그인 == 단일행 조회
		// sqlSession.selectOne("mapper파일의namespace.sql구문의id값",(쿼리문이미완성이라면)넘길값);
		return sqlSession.selectOne("memberMapper.loginMember", m);
		
	}
	
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		
		return sqlSession.insert("memberMapper.insertMember", m);
		
	}
	
	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		
		return sqlSession.update("memberMapper.updateMember", m);
		
	}
	
	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		
		// 1 또는 0이 리턴되어 돌아갈 것
		return sqlSession.update("memberMapper.deleteMember", userId);
	}

}