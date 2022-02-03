package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomRepositorySupport roomRepositorySupport;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionRepositorySupport sessionRepositorySupport;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileUploadService fileUploadService;

    @Override
    public Room createRoom(RoomCreatePostReq req, MultipartFile multipartFile) {
        Room room = new Room();

        room.setTitle(req.getTitle());
        room.setDescription(req.getDescription());
        room.setCapacity(req.getCapacity());
        if (!req.getPassword().isEmpty()) {
            room.setPassword(req.getPassword());
            room.setLocked(true);
        }
        else
            room.setLocked(false);

        System.out.println("PASSWORD :::::::::: " + req.getPassword());
        System.out.println("SETLOCKED :::::::::: " + room.isLocked());

        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        room.setStartTime(curDateTime);

        room.setThumbnailUrl("dummy.png");    // 임시 썸네일 경로
        room = roomRepository.save(room);       // 썸네일 경로 없이 방 생성
        sessionRepository.flush();
        System.out.println(room.toString());

        return this.updateThumbnail(room.getId(), multipartFile);   // 썸네일 경로 업데이트
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
    public void closeAllUserSession(Long roomId) {
        List<User> userList = getRoomUserListByRoomId(roomId);
        for (User user : userList) {
            updateSessionEndTime(roomId, user.getId());
        }
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
    public boolean isNotSessionExist(Long roomId) {
        List<Session> list = sessionRepository.findByRoomId(roomId);

        if (list.isEmpty()) return true;
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
        if (room.getPassword() == null) {
            Session session = new Session();
            session.setUser(user);
            session.setRoom(room);
            sessionRepository.save(session);
            return true;
        }
        if (room.getPassword().equals(password)) {
            Session session = new Session();
            session.setUser(user);
            session.setRoom(room);
            sessionRepository.save(session);
            return true;
        }

        return false;
    }

    @Override
    public Room deleteRoom(long roomId) {
        // 파티룸 삭제
        //TODO: findbyid를 가장 최근 값 하나만 찾아내기
        Room room = roomRepository.findById(roomId).get();
        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        room.setEndTime(curDateTime);
        return roomRepository.save(room);
    }

    @Override
    public boolean isSessionClosed(Long roomId) {
        Room room = roomRepositorySupport.getActiveRoomByRoomId(roomId);
        return room != null;
    }

    @Override
    public boolean isNotQualifiedHost(Long roomId, Long userId) {
        Session session = sessionRepositorySupport.getSessionByRoomIdAndHostId(roomId, userId);
        return session == null;
    }

    @Override
    // 이미 세션에 접속한 사용자가 다른 세션에 접근할 때 예외처리
    public boolean isUserAccessOtherSession(Long userId) {
        return sessionRepositorySupport.isUserAccessOtherSession(userId);
    }

    @Override
    // 해당 방에 접속해있는 사용자인지 확인
    public boolean isUserNotInCurrentSession(Long roomId, Long userId) {
        return sessionRepositorySupport.isUserNotInCurrentSession(roomId, userId);
    }

    @Override
    public boolean isSelectedHostIsNone(List<RoomHostUpdateReq> req) {
        for (RoomHostUpdateReq request : req) {
            if (request.getAction() == 1) return false;
        }
        return true;
    }

    @Override
    // 사용자가 방에 입장
    public void createSession(Long roomId, Long userId, boolean isHost) {
        Room room = roomRepository.findById(roomId).get();
        User user = userRepository.findByAccountId(Long.toString(userId));
        Session session = new Session();

        if(room == null || user == null)
            return;

        session.setRoom(room);
        session.setUser(user);
        session.setHost(isHost);

        sessionRepository.save(session);
    }

    @Override
    // 썸네일 경로 업데이트
    public Room updateThumbnail(long roomId, MultipartFile multipartFile) {
        Room room = this.findByRoomId(roomId);  // 썸네일 경로가 없는 룸 얻어옴
        String thumbnailPath = fileUploadService.saveFile(multipartFile, roomId);   // 썸네일 경로 생성

        room.setThumbnailUrl(thumbnailPath);    // 썸네일 경로 설정
        System.out.println(room.toString());
        return roomRepository.save(room);   // 섬네일 경로를 업데이트
    }
}
