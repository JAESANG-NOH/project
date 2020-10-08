package com.pj.util;

import org.springframework.stereotype.Service;

@Service("Util.utilService")
public class Util {
	public String paging(int current_page, int total_page, String list_url) {
		StringBuffer sb = new StringBuffer();
		
		int numPerBlock = 10;
		int currentPageSetup;
		int n, page;
		if(current_page < 1 || total_page < 1) {
			sb.append("등록된 게시물이 없습니다.");
			return sb.toString();
		}
		
		if(list_url.indexOf("?") != -1) {
			list_url += "&";
		} else {
			list_url += "?";
		}
		currentPageSetup = (current_page/numPerBlock) * numPerBlock;
		if(current_page % numPerBlock == 0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}
		
		sb.append("<div id='paginate'>");
		n = current_page-numPerBlock;
		if(total_page>numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='"+list_url+"page=1'>첫</a>");
			sb.append("<a href='"+list_url+"page="+n+"'>이전</a>");
		}
		
		page = currentPageSetup+1;
		while (page<=total_page && page <=(currentPageSetup+numPerBlock)) {
			if(page==current_page) {
				sb.append("<span class='curBox'>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"'class=numBox'>"+page+"</a>");
			}
			page++;
		}
		
		n=current_page+numPerBlock;
		if(n>total_page) {
			n = total_page;
		}
		
		if(total_page-current_page>numPerBlock) {
			sb.append("<a href='"+list_url+"page="+n+"'>다음</a>");
			sb.append("<a href='"+list_url+"page="+total_page+"'>마지막");
		}
		sb.append("</div>");
		return sb.toString();
	}
}
