package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomService {
	Room createRoom(RoomCreatePostReq roomCreatePostReq);
	List<User> getRoomUserListByRoomId(Long roomId);
	List<Session> getSessionsByRoomId(Long roomId);
	void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> updateHostReq);
	void updateSessionEndTime(String userid, Long roomId);
    void roomEntryPassword(User user, Long roomId, RoomEntryPostReq req);
}
