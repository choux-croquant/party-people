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

@Service("roomService")
public class RoomServiceImpl implements RoomService{
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionRepositorySupport sessionRepositorySupport;

    @Override
    public Room roomEntry(Room room) {
        return null;
    }

    @Override
    public Room createRoom(RoomCreatePostReq roomCreatePostReq, User user) {
        Room room = new Room();
        Session session = new Session();

        room.setTitle(roomCreatePostReq.getTitle());
        room.setDescription(roomCreatePostReq.getDescription());
        room.setThumbnailUrl(roomCreatePostReq.getThumbnail_url());
        room.setCapacity(roomCreatePostReq.getCapacity());
        room.setPassword(roomCreatePostReq.getPassword());
        room.setActive(roomCreatePostReq.getIs_active());
        room.setLocked(roomCreatePostReq.getIs_locked());

        /*
        // session테이블과 room테이블과의 join을 실행하기 위해 room 테이블에서 room_id 가져올 필요있음
        session.setUser(user);
        session.setRoom(room);
        session.setActive(roomCreatePostReq.getIs_active());
        session.setHost(true);

        sessionRepository.save(session);

        this.roomEntry();*/

        return roomRepository.save(room);
    }

    @Override
    public Room createRoom(RoomCreatePostReq roomCreatePostReq) {
        return null;
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
    public void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> updateHostReq) {
        for (RoomHostUpdateReq host : updateHostReq) {
            Session updatedSession = sessionRepositorySupport.findSessionByRoomIdAndUserId(roomId, host.getId());
            if (host.getAction()==0) updatedSession.setHost(false);
            else updatedSession.setHost(true);
            sessionRepository.save(updatedSession);
        }
    }

    @Override
    public void updateSessionEndTime(String userid, Long roomId) {

    }

    @Override
    public void roomEntryPassword(User user, Long roomId, RoomEntryPostReq req) {
        Session session = new Session();
    }
}
