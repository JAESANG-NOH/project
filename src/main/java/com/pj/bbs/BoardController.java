package com.pj.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.cj.Session;
import com.pj.member.SessionInfo;
import com.pj.util.Util;

@Controller("bbs.bbsController")
@RequestMapping("/bbs/*")
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private Util util;
	
	@RequestMapping("list")
	public String list(
			@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(defaultValue="all") String search,
			@RequestParam(defaultValue="") String searchKey,
			Model model,
			HttpServletRequest req,
			HttpSession session
			) throws Exception {
		System.out.println("check3");
		String cp = req.getContextPath();
		
		if(session.equals(null)) {
			model.addAttribute("message","로그인 후 시도해주세요.");
			return "home/home";
		}
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0; 
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchKey = URLDecoder.decode(searchKey,"UTF-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", searchKey);
		map.put("search", search);
		
		try {
			dataCount = service.dataCount(map);
			if(dataCount != 0) {
				total_page = dataCount/rows +(dataCount % rows > 0 ? 1 :0);
			}
			if(total_page < page) {
				page = total_page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		int offset = (page-1)*rows;
		if(offset < 0){
			offset = 0;
		}
		
		map.put("offset", offset);
		map.put("rows", rows);
		List<Board> list = null;
		int listNum;
		try {
			list = service.listBoard(map);
			int n = 0;
			listNum = 0;
			for(Board dto : list) {
				listNum = dataCount - (offset + n);
				dto.setListNum(listNum);
				n++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		String query = "";
		String listUrl;
		String articleUrl;
		if(searchKey.length()!=0) {
			query = "search="+search+"&searchKey="+URLEncoder.encode(searchKey, "UTF-8");
		}
		
		listUrl = cp+"/bbs/list";
		articleUrl = cp+"/bbs/page?page="+page;
		
		if(query.length()!=0) {
			listUrl = listUrl + "?" + query;
			articleUrl = articleUrl + "&" + query;
		}
		
		String paging = util.paging(page, total_page, listUrl);
		System.out.println(paging);
		model.addAttribute("list",list);
		model.addAttribute("articleUrl",articleUrl); 
		model.addAttribute("page",page);
		model.addAttribute("total_page",total_page);
		model.addAttribute("dataCount",dataCount);
		model.addAttribute("paging",paging);
		
		model.addAttribute("search",search);
		model.addAttribute("searchKey",searchKey);
		return "bbs/list";
	}
	
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String readBoard(
			@RequestParam int num,
			@RequestParam int page,
			@RequestParam(defaultValue = "all") String search,
			@RequestParam(defaultValue = "") String searchKey,
			Model model
			) throws Exception {
		searchKey = URLDecoder.decode(searchKey,"utf-8");
		
		String query = "page="+page;
		if(searchKey.length()!=0) {
			query+="&search="+search+"&searchKey="+URLDecoder.decode(searchKey,"UTF-8");
		}
		
		Board dto = null;
		Board preReadBoard = null;
		Board nextReadBoard = null;
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("searchKey", searchKey);
		
		try {
			service.updateHitCount(num);
			dto = service.readBoard(num);	
			preReadBoard = service.preReadBoard(map);
			nextReadBoard = service.nextReadBoard(map);
			if(dto==null) {
				return "redirect:/bbs/list?"+query;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		model.addAttribute("preReadBoard",preReadBoard);
		model.addAttribute("nextReadBoard", nextReadBoard);
		model.addAttribute("dto",dto);
		model.addAttribute("page",page);
		model.addAttribute("query",query);
		return "bbs/article";
	}
	
	@RequestMapping(value="insert",method=RequestMethod.GET)
	public String insertbbs(
			Model model
			) {
		model.addAttribute("state","insert");
		return "bbs/created";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insertSubmitbbs(
			Board dto,
			HttpSession session,
			HttpServletRequest req
			) throws Exception {
		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			dto.setUserId(info.getUserId());
			service.insertBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String updatebbs(
			Model model,
			@RequestParam int num
			) throws Exception {
		try {
			service.readBoard(num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("state","update");
		return "/bbs/created";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String updateSubmitbbs(
			Board dto
			) throws Exception{
		try {
			service.updateBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String deletebbs(
			@RequestParam int num,
			HttpSession session
			) throws Exception {
			Map<String, Object> map = new HashMap<>();
			try {
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				map.put("userId", info.getUserId());
				map.put("num", num);
				service.deleteBoard(map);
;			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		return "redirect:/bbs/list";
	}
}
