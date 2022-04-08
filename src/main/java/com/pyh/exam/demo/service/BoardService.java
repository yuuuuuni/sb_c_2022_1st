package com.pyh.exam.demo.service;

import org.springframework.stereotype.Service;

import com.pyh.exam.demo.repository.BoardRepository;
import com.pyh.exam.demo.vo.Board;

@Service
public class BoardService {
	private BoardRepository boardRepository;

	// 생성자 (@Autowired 해주는거랑 같은 의미 - @Autowired 빼고 생성자로 바꿔줌)
	public BoardService(BoardRepository boardRepository) { // BoardRepository 객체를 주입식으로 투입
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}
}
