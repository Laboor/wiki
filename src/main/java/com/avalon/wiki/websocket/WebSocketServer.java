package com.avalon.wiki.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/ws/{token}")
public class WebSocketServer {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        sessionMap.put(token, session);
        int cnt = onlineCount.incrementAndGet();
        LOG.info("有新连接：session id：{}，当前连接数：{}", session.getId(), cnt);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            if (session.equals(entry.getValue())) {
                sessionMap.remove(entry.getKey());
                break;
            }
        }
        int cnt = onlineCount.decrementAndGet();
        LOG.info("连接关闭，session id：{}！当前连接数：{}", session.getId(), cnt);
    }

    /**
     * 收到消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("收到消息：{}", message);
    }

    /**
     * 连接错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("发生错误：{}，Session ID：{}", error.getMessage(), session.getId(), error);
    }

    /**
     * 群发消息
     */
    public void massInfo(String message) {
        for (Session session : sessionMap.values()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOG.error("推送消息失败：内容：{}", message);
            }
            LOG.info("推送消息：内容：{}", message);
        }
    }

    /**
     * 单发消息
     */
    public void sendInfo(String token, String message) {
        Session session = sessionMap.get(token);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            LOG.error("推送消息失败：token：{} 内容：{}", token, message);
        }
        LOG.info("推送消息：token：{} 内容：{}", token, message);
    }

    /**
     * 获取连接数
     */
    public int getOnlineCount() {
        return onlineCount.get();
    }

}
