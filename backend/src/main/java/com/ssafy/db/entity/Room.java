package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 파티룸 모델 정의.
 */
@Entity
@Getter
@Setter
@DynamicInsert
public class Room extends BaseEntity{
    private String title;
    private String description;
    private String thumbnailUrl;
    private String capacity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isLocked;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "room")
    private List<Session> sessions;

    @OneToMany(mappedBy = "room")
    private List<RoomTag> roomTags;
}
