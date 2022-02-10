package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomTag;
import com.ssafy.db.entity.Tag;
import com.ssafy.db.repository.*;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *	미팅룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private RoomTagRepository roomTagRepository;

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
				HashMap<Room, Integer> roomTagMap = new HashMap<>();

				// tag 테이블에서 hashtags에 해당하는 태그 리스트를 모두 가져옴
				List<Tag> tagList = tagRepository.findByTagNameIn(hashtags);

				for (Tag tag : tagList) {
					// roomTag 테이블에서 각 hashtag에 해당하는 룸 리스트 모두 가져옴
					List<RoomTag> searchedRooms = roomTagRepository.findByTag(tag);

					// 맵에 룸과 찾는 해시태그를 몇개 포함하는지 저장
					for (RoomTag searchedRoom : searchedRooms) {
						Room curRoom = searchedRoom.getRoom();
						roomTagMap.computeIfPresent(curRoom, (k, v) -> v+1);
						roomTagMap.putIfAbsent(curRoom, 1);
					}
				}

				// 조회하는 해시태그 개수만큼 해시태그를 포함한 룸만 반환
				List<Room> roomContainingHashtags = new ArrayList<>();
				int size = hashtags.length;
				for (Map.Entry<Room, Integer> entry : roomTagMap.entrySet()) {
					if (entry.getValue() == size) roomContainingHashtags.add(entry.getKey());
				}

				roomList = new PageImpl<>(roomContainingHashtags, sort, roomContainingHashtags.size());
				break;
		}
		return roomList;
	}
}
