package com.avalon.wiki.service.impl;

import com.avalon.wiki.service.iface.IWebSocketService;
import com.avalon.wiki.websocket.WebSocketServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WebSocketService implements IWebSocketService {

    @Resource
    public WebSocketServer webSocketServer;

    @Override
    @Async("asyncExecutor")
    public void sendInfo(String token, String message) {
        webSocketServer.sendInfo(token, message);
    }

    @Override
    @Async("asyncExecutor")
    public void massInfo(String message) {
        webSocketServer.massInfo(message);
    }

    @Override
    public int getOnlineCount() {
        return webSocketServer.getOnlineCount();
    }
}
