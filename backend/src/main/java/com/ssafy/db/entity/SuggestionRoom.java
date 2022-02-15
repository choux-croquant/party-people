package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 인 메모리 파티룸 모델 정의.
 */
@Entity
@Table(name = "room_in_memory")
@Getter
@Setter
public class SuggestionRoom extends BaseEntity{
    private String title;
    private String description;
}
