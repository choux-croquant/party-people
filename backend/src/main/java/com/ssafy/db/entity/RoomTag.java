package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 파티룸 태그 모델 정의.
 */
@Entity
@Getter
@Setter
public class RoomTag extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Override
    public String toString() {
        return "RoomTag{" +
                "tag=" + tag +
                '}';
    }
}
