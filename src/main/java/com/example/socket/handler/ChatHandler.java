package com.example.socket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * 텍스트 기반 채팅 구현을 위해 TextWebSocketHandler을 상속받는다.
 * client로부터 받은 메세지를 Log로 출력하고,
 * 클라이언트가 연결 구축, 연결 해제할 때 메시지를 보낸다.
 * */
@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> clientSessions = new ArrayList<>(); //여러개의 클라이언트가 접속할 수 있음.

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        String payload = message.getPayload();
        log.info("payload : " + payload);
        //JSON에서 data에 해당하는 부분
        for(WebSocketSession client : clientSessions){
            client.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clientSessions.add(session);
        log.info(session + "새로운 클라이언트 접속, 현제 인원 수 [{}]", clientSessions.size());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        clientSessions.remove(session);
        log.info(session + "새로운 클라이언트 접속 해제, 현제 인원 수 [{}]", clientSessions.size());

    }
}
