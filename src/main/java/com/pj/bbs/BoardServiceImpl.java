package com.pj.bbs;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.common.dao.CommonDAO;

@Service("bbs.boardService")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private CommonDAO dao;
	
	
	@Override
	public List<Board> listBoard(Map<String, Object> map) {
		List<Board> list = null;
		try {
			list = dao.selectList("selectList", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int num=0;;
		try {
			num = dao.selectOne("selectDataCount", map);
			System.out.println(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public void updateHitCount(int num) throws Exception {
		try {
			dao.updateData("updateHitCount", num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void insertBoard(Board dto) throws Exception {
		try {
			dao.insertData("insertBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateBoard(Board dto) throws Exception {
		try {
			dao.updateData("updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("deleteBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Board readBoard(int num) throws Exception {
		Board dto = null;
		try {
			dto = dao.selectOne("selectReadBoard",num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dto;
	}

	@Override
	public Board preReadBoard(Map<String, Object> map) {
		Board dto = null; 
		try {
			dto = dao.selectOne("selectPreReadBoard",map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public Board nextReadBoard(Map<String, Object> map) {
		Board dto = null;
		try {
			dto = dao.selectOne("selectNextReadBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
		
	}

	
}
