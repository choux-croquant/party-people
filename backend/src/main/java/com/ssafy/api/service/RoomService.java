package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomService {
	Room createRoom(RoomCreatePostReq roomCreatePostReq, Long userId);
	Room deleteRoom(long roomId);
	List<User> getRoomUserListByRoomId(Long roomId);
	List<Session> getSessionsByRoomId(Long roomId);
	void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> updateHostReq);
	void updateSessionEndTime(Long roomId, Long userId);
	Room findByRoomId(Long roomId);
	boolean roomEntry(User user, Long roomId, String password);
	boolean checkRoomUserExist(Long roomId);
	boolean isNotSessionExist(Long roomId);
	boolean isSessionClosed(Long roomId);
}
