package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 태그 모델 정의.
 */
@Entity
@Getter
@Setter
public class Tag extends BaseEntity{
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<UserTag> userTags;

    @OneToMany(mappedBy = "tag")
    private List<RoomTag> roomTags;
}
