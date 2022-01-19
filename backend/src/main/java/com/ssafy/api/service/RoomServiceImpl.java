package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roomService")
public class RoomServiceImpl implements RoomService{
    @Autowired
    RoomRepository roomRepository;

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
}
