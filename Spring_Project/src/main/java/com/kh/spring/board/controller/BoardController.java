package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 메뉴바 클릭 시 => list.bo (기본적으로 1번 페이지 요청)
	// 페이징바 클릭 시 => list.bo?cpage=요청하는페이지수
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		
		// System.out.println("cpage: " + currentPage);
		
		int listCount = boardService.selectListCount();
		
		int pageLimit = 10;
		int boardLimit = 5;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Board> list = boardService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		// /WEB-INF/views/board/boardListView.jsp
		return "board/boardListView";
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		
		// /WEB-INF/views/board/boardEnrollForm.jsp
		return "board/boardEnrollForm";
	}
	
	// * 만약 다중파일 업로드를 구현하고 싶다면?
	// => jsp 에서 여러 개의 input type="file" 요소에 모두 동일한 name 속성을 부여 (ex. upfile)
	// => Controller의 메소드에서 MultipartFile[] upfile 또는 List<MultipartFile> upfile로 받으면 됨!
	// (0번째 인덱스에서부터 차곡차곡 첨부파일의 정보들이 담겨 있음 : 반복문 활용 가능)
	
	@RequestMapping("insert.bo")
	public ModelAndView insertBoard(Board b, MultipartFile upfile, HttpSession session, ModelAndView mv) {
		
		// System.out.println(b);
		// System.out.println(upfile);
		
		// 요청 시 name 속성과 필드명을 정확하게 맞췄음에도 불구하고 제대로 된 전달값이 안 들어옴
		// 요청 시 분명히 파일을 넘겼음에도 불구하고 upfile 값이 null
		// => 파일 업로드에 필요한 Spring 라이브러리를 pom.xml에 추가하지 않았기 때문
		// 파일 업로드용 라이브러리: commons-fileupload, commons-io
		
		// MultipartFile: 첨부파일을 선택했든 안 했든 생성된 객체 (즉, null이 아님)
		//                다만, filename 필드에 원본명이 있냐 없냐의 차이로 첨부파일이 있는지 없는지 확인 가능함
		
		// 전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버로 업로드
		// => 원본명, 서버에 업로드된 경로를 이어 붙이기
		if(!upfile.getOriginalFilename().contentEquals("")) {
			/*
			// 파일명 수정 작업 후 서버에 업로드시키기
			// 예) "flower.png" => "2022112210405012345.png"
			// 1. 원본파일명 뽑아오기
			String originName = upfile.getOriginalFilename(); // "flower.png"
			
			// 2. 시간 형식을 문자열로 뽑아내기
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "2022112210405012345.png"
			
			// 3. 뒤에 붙을 5자리 랜덤값 뽑기
			int ranNum = (int)(Math.random() * 90000) + 10000; // 5자리 랜덤값
			
			// 4. 원본 파일로부터 확장자만 뽑기
			// String ext = originName.substring(마지막 .의위치);
			String ext = originName.substring(originName.lastIndexOf(".")); // ".png"
			
			// 5. 모두 이어 붙이기
			String changeName = currentTime + ranNum + ext;
			
			// 6. 업로드 하고자 하는 서버의 물리적인 실제 경로 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			// 7. 경로와 수정 파일명을 합체 후 파일을 업로드 해 주기
			try {
				upfile.transferTo(new File(savePath + changeName)); // File의 매개변수로 경로를 지정해 주면 File이 만들어지면서 경로로 들어감
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			
			String changeName = saveFile(upfile, session);
			
			// 8. 원본명, 서버에 업로드된 수정명을 Board b에 담기
			// => boardTitle, boardContent, boardWriter 필드에만 담겨 있음
			// => originName, changeName 필드에도 전달된 파일에 대한 정보를 담을 것임!
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName); // 실제 경로도 같이 이어붙일 것(FILE_PATH 컬럼을 따로 빼두지 않음)
			
		}
		
		// 넘어온 첨부파일이 있을 경우 b: 제목, 작성자, 내용, 원본파일명, 경로 + 수정파일명
		// 넘어온 첨부파일이 없을 경우 b: 제목, 작성자, 내용
		int result = boardService.insertBoard(b);
		
		if(result > 0) { // 성공 => 게시글 리스트 페이지로 url 재요청(list.bo)
			
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
			mv.setViewName("redirect:/list.bo");
			
		} else { // 실패 => 에러페이지로 포워딩
			
			// mv.addObject("errorMsg", "게시글 작성 실패");
			// mv.setViewName("common/errorPage");
			
			// addObject 메소드의 반환형은 ModelAndView 타입임
			// => 다음과 같이 메소드 체이닝 가능함
			mv.addObject("errorMsg", "게시글 작성 실패").setViewName("common/errorPage");
		}
		
		return mv;
		
	}
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, ModelAndView mv) {
		
		// bno에는 상세 조회하고자 하는 해당 게시글 번호가 담겨 있음
		
		// 1. 해당 게시글 조회 수 증가용 서비스 먼저 호출 결과 받기(update 하고 오기)
		int result = boardService.increaseCount(bno);
		
		if(result > 0) { // 성공적으로 조회 수 증가가 일어났다면
			
			// 2. 상세 조회 요청
			// => boardDetailView.jsp 상에 필요한 데이터
			Board b = boardService.selectBoard(bno);
			
			// 조회된 데이터를 담아서 board/boardDetailView.jsp로 포워딩
			mv.addObject("b", b).setViewName("board/boardDetailView");
			
		} else { // 조회 수 증가가 일어나지 않음 => 실패
			
			mv.addObject("errorMsg", "게시글 상세 조회 실패").setViewName("common/errorPage");
			
		}
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping(value="rinsert.bo", produces="text/html; charset=UTF-8")
	public String ajaxInsertReply(Reply r) {
		
		// System.out.println(r);
		
		int result = boardService.insertReply(r);
		
		// if(result > 0) {
		// } else {
		// }
		
		// if문 써도 무방하나 삼항 연산자를 사용해 볼 것!
		
		return (result > 0) ? "success" : "fail";
		
	}
	
	// 현재 넘어온 첨부파일 그 자체를 수정명으로 서버의 폴더에 저장시키는 메소드 (일반 메소드 - 요청 처리 목적이 아닌 일반 메소드 목적!)
	// => Spring의 Controller 메소드가 반드시 요청을 처리하는 메소드로 이루어져야 하는 건 아님!
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		// 파일명 수정 작업 후 서버에 업로드시키기
		// 예) "flower.png" => "2022112210405012345.png"
		// 1. 원본파일명 뽑아오기
		String originName = upfile.getOriginalFilename(); // "flower.png"
		
		// 2. 시간 형식을 문자열로 뽑아내기
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "2022112210405012345.png"
		
		// 3. 뒤에 붙을 5자리 랜덤값 뽑기
		int ranNum = (int)(Math.random() * 90000) + 10000; // 5자리 랜덤값
		
		// 4. 원본 파일로부터 확장자만 뽑기
		// String ext = originName.substring(마지막 .의위치);
		String ext = originName.substring(originName.lastIndexOf(".")); // ".png"
		
		// 5. 모두 이어 붙이기
		String changeName = currentTime + ranNum + ext;
		
		// 6. 업로드 하고자 하는 서버의 물리적인 실제 경로 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		System.out.println(savePath);
		
		// 7. 경로와 수정 파일명을 합체 후 파일을 업로드 해 주기
		try {
			upfile.transferTo(new File(savePath + changeName)); // File의 매개변수로 경로를 지정해 주면 File이 만들어지면서 경로로 들어감
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
		
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) {
		
		// System.out.println(bno);
		
		int result = boardService.deleteBoard(bno);
		
		if(result > 0) { // 게시글 삭제 성공
			
			// 첨부파일이 있었을 경우 => 수정명 찍힘
			// 첨부파일이 없었을 경우 => 빈 문자열 찍힘
			// filePath에는 해당 게시글의 수정파일명이 들어 있음, 빈문자열이 아니라면 첨부파일이 있었던 경우일 것!
			if(!filePath.equals("")) { // 첨부파일이 있었을 경우
				
				String realPath = session.getServletContext().getRealPath(filePath);
				new File(realPath).delete();
			}
			
			// 게시판 리스트 페이지 url 재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			
			return "redirect:/list.bo";
			
		} else { // 게시글 삭제 실패
			
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		
		// 게시글 수정 페이지를 포워딩 하기 전에 우선적으로 해당 게시글 정보를 조회해 올 것
		Board b = boardService.selectBoard(bno); // 기존의 상세보기 서비스를 재활용
		
		model.addAttribute("b", b);
		
		return "board/boardUpdateForm";
		
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		if(!reupfile.getOriginalFilename().equals("")) { // 새로 넘어온 첨부파일이 있는 경우 == reupfile이 빈 문자열이 아니라면
			
			// System.out.println(b);
			// b의 boardNo: 내가 수정하고자 하는 게시글의 번호 (WHERE절)
			// b의 boardTitle: 수정할 제목 (SET절)
			// b의 boardContent: 수정할 내용 (SET절)
			// b의 originName: 기존 첨부파일의 원본명
			// b의 changeName: 기존 첨부파일의 수정명

			// 1. 기존 첨부파일이 있었을 경우 => 기존 첨부파일 찾아서 삭제
			if(b.getOriginName() != null) {
				String realPath = session.getServletContext().getRealPath(b.getChangeName());
				new File(realPath).delete();
			}
			
			// 2. 새로 넘어온 첨부파일을 수정명으로 바꾸고 서버에 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			// 3. b 객체에 새로 넘어온 첨부파일에 대한 원본명, 수정 파일명 필드에 담기
			b.setOriginName(reupfile.getOriginalFilename()); // 원본 파일명 덮어 씌움
			b.setChangeName("resources/uploadFiles/" + changeName);
			
		} // 새롭게 들어온 첨부파일이 없다면 굳이 파일의 원본/수정명을 바꿀 필요 없으므로 else문 없이 끝!
				
				/*
				 * b에 무조건 담겨 있는 내용
				 * boardNo, boardTitle, boardContent
				 * 
				 * 추가적으로 고려해야 할 경우의 수
				 * 1. 새로 첨부된 파일 X, 기존 첨부파일 x
				 * => originName: null, changeName: null
				 * 
				 * 2. 새로 첨부된 파일 X, 기존 첨부파일 O
				 * => originName: 기존 첨부파일 원본명
				 *    changeName: 기존 첨부파일 수정명 + 파일 경로
				 *    
				 * 3. 새로 첨부된 파일 O, 기존 첨부파일 X
				 * => originName: 새로 첨부된 파일의 원본명
				 *    changeName: 새로 첨부된 파일의  수정명 + 파일 경로
				 * 
				 * 4. 새로 첨부된 파일 O, 기존 첨부파일 O
				 * => 기존 파일 삭제
				 * => 새로이 전달된 파일을 서버에 업로드
				 * => originName: 새로 첨부된 파일의 원본명
				 *    changeName: 새로 첨부된 파일의  수정명 + 파일 경로
				 */
				
				// Service단으로 b 보내기
				int result = boardService.updateBoard(b);
				
				if(result > 0) { // 게시글 수정 성공
					
					session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");
					
					// 게시글 상세보기 페이지로 url 재요청
					return "redirect:/detail.bo?bno=" + b.getBoardNo();
					
				} else { // 게시글 수정 실패
					
					model.addAttribute("errorMsg", "게시글 수정 실패");
					return "common/errorMsg";
					
				}
		
	}
	
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces="application/json; charset=UTF-8")
	public String ajaxSelectReplyList(int bno) {
		
		// System.out.println(bno);
		
		ArrayList<Reply> list = boardService.selectReplyList(bno);
		
		return new Gson().toJson(list);
		
	}

}