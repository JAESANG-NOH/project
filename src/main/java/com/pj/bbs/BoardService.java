package com.pj.bbs;

import java.util.List;
import java.util.Map;

public interface BoardService {
	public List<Board> listBoard(Map<String, Object> map);
	public int dataCount(Map<String, Object> map);
	public Board readBoard(int num) throws Exception;
	public void updateHitCount(int num) throws Exception;
	public void insertBoard(Board dto) throws Exception;
	public void updateBoard(Board dto) throws Exception;
	public void deleteBoard(Map<String, Object> map) throws Exception;
}
