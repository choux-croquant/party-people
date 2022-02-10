package com.ssafy.db.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 태그와 룸 테이블 조인 테이블인 룸 태그 관련 디비 쿼리 생성을 위한 JPA QueryDSL 클래스
 */
@Repository
public class RoomTagRepositorySupport extends QuerydslRepositorySupport {
    @Autowired
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private QRoomTag qRoomTag = QRoomTag.roomTag;
    private QRoom qRoom = QRoom.room;

    public RoomTagRepositorySupport(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        super(RoomTag.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    public Page<Room> getRoomByTag(List<Tag> hashtags, Pageable sort) {
        JPQLQuery<Room> query = jpaQueryFactory.select(qRoomTag.room).from(qRoomTag)
                .where(qRoomTag.tag.in(hashtags))
                .groupBy(qRoom)
                .having(qRoomTag.tag.count().eq((long) hashtags.size()));

        List<Room> roomList = getQuerydsl().applyPagination(sort, query).fetch();
        return new PageImpl<>(roomList, sort, query.fetchCount());
    }
}
