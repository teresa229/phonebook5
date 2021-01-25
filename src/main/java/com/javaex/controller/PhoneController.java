package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller  //첫자 대문자 주의
@RequestMapping(value="/phone")
public class PhoneController {

	// 필드
	@Autowired
	private PhoneDao phoneDao; //대상에 넣지 않으면 오류가 난다.  applicationContext.xml
	
	// 생성자 - 디폴트 사용
	// 메소드 g/s
	// 메소드 일반

	// list
	@RequestMapping(value ="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {                                                    
		System.out.println("list");
		
		List<PersonVo> personList = phoneDao.getPersonList();
		//System.out.println(personList.toString());
		
		model.addAttribute("pList", personList); 
		
		return "list";                              
	}
	
	// writeForm
	@RequestMapping(value ="/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		
		return "writeForm";
	}
	
	// write
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name,
						@RequestParam("hp") String hp,
						@RequestParam("company") String company) {
		
		System.out.println("write");
		
		System.out.println(name + "," + hp + "," + company);
		
		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());
		
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	}
	
	// 삭제 --> 문법 : @PathVariable 간단한 url만들기
	@RequestMapping(value="/delete/{personId}", method= {RequestMethod.GET, RequestMethod.POST}) //{변수값} 무슨값을 적어도 상관없음. 2개일 수도 있다. 주소영역에 파라미터값을 넣어주는 것이다.
	public String delete(@PathVariable("personId") int id) {
		System.out.println("삭제");
		System.out.println(id);                             
		
   		phoneDao.personDelete(id);                           
		  		
		return "redirect:/phone/list";
	}
	
	// 삭제 --> @RequestMapping 약식 표현
	@RequestMapping(value="/delete2", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete2(@RequestParam("personId") int id) { 
		System.out.println("삭제");
		System.out.println(id);                              
		        
   		phoneDao.personDelete(id);                           
		  		
		return "redirect:/phone/list";
	}

	// modifyForm
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET,RequestMethod.POST})
	public String modifyForm(@RequestParam("personId") int id, Model model) {  //Model model의 위치는 앞뒤 상관없다.
		System.out.println("modifyForm");
		System.out.println(id);
		
		PersonVo personVo = phoneDao.getPerson(id);
	
		System.out.println(personVo.toString());   //DB에서 잘 받아왔는지 확인!

		model.addAttribute("pvo", personVo);       //(별명, 실제 데이터 주소) -> 알아서 request Attribute에 넣어준다 ->forward가 이루어진다.
		
		return "modifyForm"; 
	}
	
	// modifyForm2
	@RequestMapping(value="/modifyForm2", method= {RequestMethod.GET,RequestMethod.POST})
	public String modifyForm2(@RequestParam("personId") int id, Model model) {  //Model model의 위치는 앞뒤 상관없다.
		System.out.println("modifyForm2");
		System.out.println(id);
		
		Map<String, Object> personMap = phoneDao.getPerson2(id); //map - import
		model.addAttribute("pMap", personMap);  //(별명, 실제 데이터 주소) -> 알아서 request Attribute에 넣어준다 ->forward가 이루어진다.
		
		return "modifyForm2"; 
	}
	
	// 수정 --> 문법 : @ModelAttribute
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute PersonVo personVo) {
		//@ModelAttribute + 자료형 + 이름 담아준다-> 디폴트로 생성자가 있어야 함 -> set으로 데이터를 불러온다.
		
		System.out.println("수정"); 
		System.out.println(personVo.toString());
		
		phoneDao.personUpdate(personVo);  
		
		return "redirect:/phone/list";
	}

	// 수정2 --> @RequestParam
	@RequestMapping(value="/modify2", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify2(@RequestParam("personId") int personId,
			              @RequestParam("name") String name,
			              @RequestParam("hp") String hp,
			              @RequestParam("company") String company) {
		System.out.println("수정2");
		System.out.println(personId+ "," + name +","+ hp +","+ company);		

		phoneDao.personUpdate2(personId, name, hp, company);  //디비에 저장이 확실하게 되었는지 확인해주는 것이 좋다. --> sql로 확인
		
		return "redirect:/phone/list";
	}
	

}
