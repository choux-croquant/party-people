package com.ssafy.api.service;

import io.openvidu.java.client.Session;

public interface OpenviduService {
    Session createSession();
    String createConnection(Session session);
}
