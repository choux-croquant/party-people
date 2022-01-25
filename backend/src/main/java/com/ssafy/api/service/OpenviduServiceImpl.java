package com.ssafy.api.service;

import io.openvidu.java.client.*;
import org.springframework.stereotype.Component;

@Component
public class OpenviduServiceImpl implements OpenviduService{
    OpenVidu openVidu;
    SessionProperties sessionProperties;

    OpenviduServiceImpl(){
        this.openVidu = new OpenVidu("http://partypeople.kro.kr:5443/",
                "a106ssafy0183");
        this.sessionProperties = new SessionProperties.Builder().build();
    }

    @Override
    public Session createSession() {
        try {
            return openVidu.createSession(this.sessionProperties);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createConnection(Session session) {
        // 연결 성공 시 토큰 반환
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("user_data")
                .build();
        Connection connection = null;

        try {
            connection = session.createConnection(connectionProperties);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }

        return connection.getToken(); // Send this string to the client side
    }
}
