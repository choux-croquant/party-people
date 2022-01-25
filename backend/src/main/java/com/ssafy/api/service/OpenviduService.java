package com.ssafy.api.service;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;

public interface OpenviduService {
    Session createSession();
    boolean closeSession(Session session);
    String createConnection(Session session);
    boolean destroyConnection(Session session, Connection connection);
}
