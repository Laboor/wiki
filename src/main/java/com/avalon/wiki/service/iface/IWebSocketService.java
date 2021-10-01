package com.avalon.wiki.service.iface;

public interface IWebSocketService {

    void sendInfo(String token, String message);

    void massInfo(String message);

    int getOnlineCount();
}
