package com.pj.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pj.util.Util;

import javassist.compiler.ast.Keyword;

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
			HttpServletRequest req
			) throws Exception {
		
		String cp = req.getContextPath();
		
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
				total_page = dataCount/rows +(dataCount % rows > 0 ? 0 : 1);
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
		try {
			list = service.listBoard(map);
			int listNum = 0;
			int n = 0;
			for(Board dto : list) {
				listNum = dataCount - (offset + n);
				dto.setListnum(listNum);
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
		
		model.addAttribute("list",list);
		model.addAttribute("articleUrl",articleUrl); 
		model.addAttribute("page",page);
		model.addAttribute("total_page",total_page);
		model.addAttribute("dataCount",dataCount);
		model.addAttribute("paging",paging);
		
		model.addAttribute("search",search);
		model.addAttribute("searchKey",searchKey);
		return "list";
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
		
		service.updateHitCount(num);
		
		Board dto = service.readBoard(num);
		if(dto==null) {
			return "redirect:/bbs/list?"+query;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		model.addAttribute("dto",dto);
		model.addAttribute("page",page);
		model.addAttribute("query",query);
		return "article";
	}
	
	@RequestMapping(value="insert",method=RequestMethod.GET)
	public String insertbbs(
			Model model
			) {
		model.addAttribute("state","insert");
		return "insert";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insertSubmitbbs(
			Board dto,
			HttpServletRequest req
			) throws Exception {
		try {
			service.insertBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="update", method =RequestMethod.GET)
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
		return "insert";
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
}