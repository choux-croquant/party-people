package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QSession;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 세션 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class SessionRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QSession qSession = QSession.session;

    public List<User> findUsersByRoomId(Long roomId) {
        List<User> users = jpaQueryFactory.select(qSession.user).from(qSession)
                .where(qSession.room.id.eq(roomId)).fetch();
        return users;
    }

    public List<Session> findSessionByRoomId(Long roomId) {
        List<Session> sessions = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId)).fetch();
        return sessions;
    }

    public Session findSessionByRoomIdAndUserId(Long roomId, Long userId) {
        Session session = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.user.id.eq(userId))).fetchOne();
        return session;
    }
}
