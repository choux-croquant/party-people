package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


/**
 *	미팅룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private RoomTagRepositorySupport roomTagRepositorySupport;

	@Override
	public Page<Room> getRoomList(Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		return listRepository.findByEndTime(null, sort);
	}

	@Override
	public Page<Room> getRoomListByWord(String word, String include, Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		Page<Room> roomList = null;

		switch (include) {
			case "title" :
				roomList = listRepository.findByTitleContainingAndEndTimeIsNull(word, sort);
				break;
			case "des" :
				roomList = listRepository.findByDescriptionContainingAndEndTimeIsNull(word, sort);
				break;
			case "hashtag" :
				String[] hashtags = word.substring(1).split("#");
				roomList = roomTagRepositorySupport.getRoomTagByTagName(hashtags, sort);
				break;
		}
		return roomList;
	}
}
