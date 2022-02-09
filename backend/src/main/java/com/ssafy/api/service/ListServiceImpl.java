package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.ListRepository;
import com.ssafy.db.repository.ListRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *	미팅룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	private ListRepository listRepository;
	
	@Autowired
	private ListRepositorySupport listRepositorySupport;

	@Override
	public Page<Room> getRoomList(Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		return listRepository.findByEndTime(null, sort);
	}

	@Override
	public Page<Room> getRoomListByWord(String[] word, String include, Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		if(include.equals("title")) return listRepository.findByTitleContainingAndEndTimeIsNull(word, sort);
		return listRepository.findByDescriptionContainingAndEndTimeIsNull(word, sort);
	}
}
