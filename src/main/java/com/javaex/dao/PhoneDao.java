package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//전체 리스트 가져오기
	public List<PersonVo> getPersonList(){
		System.out.println("dao: getPersonList()");
		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList2"); //전체가져오기 sql (namespace.id)
		
		System.out.println(personList); //값을 잘 가져오는지 확인
		return personList;
		
	}
	
	//전화번호 저장
	public void personInsert(PersonVo personVo) {
		System.out.println(personVo.toString());
		sqlSession.insert("phonebook.insert", personVo); //(이름, 무조건 1개 묶어서라도 1개만 넣을 수 있다.);	
	}
	
	//전화번호 삭제 + count
	public int personDelete(int personId) {
		System.out.println("dao: personDelete()"+personId);
		
		int count = sqlSession.delete("phonebook.delete", personId);
		System.out.println(count);
		
		return count;
	}
	
	/*전화번호 삭제
	public void personDelete(int personId) {
		System.out.println("dao: personDelete()"+personId);
		
		isqlSession.delete("phonebook.delete", personId);
	}
	*/
	
	//1명 데이터 가져오기
	public PersonVo getPerson(int personId) {
		System.out.println("dao:getPerson()"+personId);
		
		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", personId); //("1명 가져오기 sql", personId); 몇 번 데이터를 가져와달라
		System.out.println(personVo.toString());
		return personVo;
	}
	
	//전화번호 수정
	public void personUpdate(PersonVo personVo) {
		System.out.println("dao:personUpdate()");
		System.out.println(personVo.toString());
		
		sqlSession.update("phonebook.update",personVo);
	}
}