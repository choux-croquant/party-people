package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.SessionRepository;
import com.ssafy.db.repository.SessionRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService{
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionRepositorySupport sessionRepositorySupport;

    @Override
    public Room createRoom(RoomCreatePostReq req) {
        Room room = new Room();

        room.setTitle(req.getTitle());
        room.setDescription(req.getDescription());
        room.setThumbnailUrl(req.getThumbnail_url());
        room.setCapacity(req.getCapacity());
        if(req.getPassword() != null) {
            room.setPassword(req.getPassword());
            room.setLocked(true);
        }

        return roomRepository.save(room);
    }

    @Override
    public List<User> getRoomUserListByRoomId(Long roomId) {
        return sessionRepositorySupport.findUsersByRoomId(roomId);
    }

    @Override
    public List<Session> getSessionsByRoomId(Long roomId) {
        return sessionRepositorySupport.findSessionByRoomId(roomId);
    }

    @Override
    public void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> req) {
        for (RoomHostUpdateReq host : req) {
            Session updatedSession = sessionRepositorySupport.findSessionByRoomIdAndUserId(roomId, host.getId());
            if (host.getAction() == 0) updatedSession.setHost(false);
            else updatedSession.setHost(true);
            sessionRepository.save(updatedSession);
        }
    }

    @Override
    public void updateSessionEndTime(Long roomId, Long userId) {
        Session updatedSession = sessionRepositorySupport.findSessionByRoomIdAndUserId(roomId, userId);
        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        updatedSession.setEndTime(curDateTime);
        sessionRepository.save(updatedSession);
    }

    @Override
    public boolean checkRoomUserExist(Long roomId) {
        List<Session> sessions = getSessionsByRoomId(roomId);
        for (Session session : sessions) {
            if (session.getEndTime() == null) return true;
        }
        return false;
    }

    @Override
    public Room findByRoomId(Long roomId) {
        return roomRepository.findById(roomId).get();
    }

    @Override
    public boolean roomEntry(User user, Long roomId, String password) {
        Room room = roomRepository.findById(roomId).get();
        // TODO: capacity 확인
        if(room.getPassword() == null) {
            Session session = new Session();
            session.setUser(user);
            session.setRoom(room);
            sessionRepository.save(session);
            return true;
        }
        if(room.getPassword().equals(password)) {
            // TODO: 세션 테이블 업데이트
            return true;
        }

        return false;
    }

    @Override
    public Room deleteRoom(long roomId) {
        // 파티룸 삭제
        Room room = roomRepository.findById(roomId).get();
        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        room.setEndTime(curDateTime);
        return roomRepository.save(room);
    }
}
