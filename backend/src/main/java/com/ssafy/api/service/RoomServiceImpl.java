package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.SessionRepository;
import com.ssafy.db.repository.SessionRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("roomService")
public class RoomServiceImpl implements RoomService{
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionRepositorySupport sessionRepositorysupport;

    @Override
    public Room createRoom(RoomCreatePostReq roomCreatePostReq) {
        Room room = new Room();

        room.setTitle(roomCreatePostReq.getTitle());
        room.setDescription(roomCreatePostReq.getDescription());
        room.setThumbnailUrl(roomCreatePostReq.getThumbnail_url());
        room.setCapacity(roomCreatePostReq.getCapacity());
        room.setPassword(roomCreatePostReq.getPassword());
        room.setActive(roomCreatePostReq.getIs_active());
        room.setLocked(roomCreatePostReq.getIs_locked());

        return roomRepository.save(room);
    }

    @Override
    public List<User> getRoomUserListByRoomId(Long roomId) {
        return sessionRepositorysupport.findUsersByRoomId(roomId);
    }

    @Override
    public List<Session> getSessionsByRoomId(Long roomId) {
        return sessionRepositorysupport.findSessionByRoomId(roomId);
    }

    @Override
    public void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> updateHostReq) {
        for (RoomHostUpdateReq host : updateHostReq) {
            Session updatedSession = sessionRepositorysupport.findSessionByRoomIdAndUserId(roomId, host.getId());
            if (host.getAction()==0) updatedSession.setHost(false);
            else updatedSession.setHost(true);
            sessionRepository.save(updatedSession);
        }
    }

    // TODO: 2022-01-20  setEndTime() 인자로 종료 시간 새롭게 설정해야함
    @Override
    public void updateSessionEndTime(Long roomId, Long userId) {
        Session updatedSession = sessionRepositorysupport.findSessionByRoomIdAndUserId(roomId, userId);
        updatedSession.setEndTime();
        sessionRepository.save(updatedSession);
    }

    @Override
    public boolean checkRoomUserExist(Long roomId) {
        List<Session> sessions = getSessionsByRoomId(roomId);
        for (Session session : sessions) {
            if (session.getEndTime()==null) return true;
        }
        return false;
    }

    @Override
    public void roomEntryPassword(User user, Long roomId, RoomEntryPostReq req) {
        Session session = new Session();
    }
}
