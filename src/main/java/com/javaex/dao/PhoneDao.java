package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//1명 데이터 가져오기 2
	public Map<String, Object> getPerson2(int personId) {
		System.out.println("dao:getPerson2()"+personId);
		
		Map<String, Object> personMap = sqlSession.selectOne("phonebook.selectOne2", personId); //PersonVo personVo 가 아니라 map으로 받는다.
		System.out.println(personMap.toString()); //2를 임의로 넣어주어야 볼수 있다. 
		//키값을 무엇으로 꺼내고 싶으냐? int id로 받기 - 넣을때 object를 넣었기 떄문에 형변환이 있어야한다.
		
		int id = Integer.parseInt(String.valueOf(personMap.get("personId")));//오류나는 원인 정리
		System.out.println(id);
		
		String name = (String)personMap.get("name");
		System.out.println(name);
		
		return personMap; //중간에 return null;로 test해 볼수 있다.
	}
	
	//전화번호 수정
	public void personUpdate(PersonVo personVo) {
		System.out.println("dao:personUpdate()"+ personVo.toString());
		System.out.println(personVo.toString());
		
		sqlSession.update("phonebook.update",personVo);
	}
	
	//전화번호 수정 1 : count로 묶기
	public int personUpdate1(PersonVo personVo) {
		System.out.println("dao:personUpdate1()");
		System.out.println(personVo.toString());
		
		int count = sqlSession.update("phonebook.update",personVo);
		System.out.println("dao: count="+count);
		return count;
	}
	
	//전화번호 수정 2
	public void personUpdate2(int personId, String name, String hp, String company) {
		System.out.println("dao: personUpdate2()="+personId+","+name+","+hp+","+company);
		
		//방법 1: vo로 묶기 - 1번 사용할려고 vo를 만드는 것 비추.
		//PersonVo personVo = new PersonVo(personId, name, hp, company);  //new PersonVo를 딱 한번만 사용할 것 같아. 한번 사용할 것 같아. 대안 -> Vo를 만들어서 사용하는 것이 아니라...map을 이용한다.
		
		//방법 2: vo대신  map사용한다. 
		Map<String, Object> personMap = new HashMap<String,Object>();// <이름,데이터-- 여러개가 섞여서 오므로 object>;
		personMap.put("id", personId);                               //("key값", 데이터);
		personMap.put("name", name);
		personMap.put("hp", hp);
		personMap.put("company", company);

		System.out.println(personMap.toString());
		
		sqlSession.update("phonebook.update2", personMap); //sqlSession.update2 안된다.정해진 update만 사용할 수 있다. //( ,이름);
	}
	
}