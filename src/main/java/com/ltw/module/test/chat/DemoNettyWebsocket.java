package com.ltw.module.test.chat;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.ltw.common.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.util.HashMap;

@Slf4j
@ServerEndpoint(host="0.0.0.0", port = "8333", path = "/device/test", maxFramePayloadLength = "6553600")
public class DemoNettyWebsocket {
    private static final String MESSAGE_ONLINE = "online";

    public void sendMessage(Session session, String message) {
        session.sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session socketSession, HttpHeaders headers){
        Long projectId = null;
//        try {
//            String projectIds = headers.get("projectId");
//            projectId  = Long.parseLong(projectIds);
//        }catch (Exception e){
//            HashMap<String, String> map = new HashMap<>();
//            map.put("message", "projectId必须为数字");
//            sendMessage(socketSession, JsonUtils.toJson(map));
//        }
//        Channel channel = socketSession.channel();
//        String channelId = channel.id().asShortText();
//        log.info("new connection channerlId:{}",channelId);
//        System.out.println("projectId------>"+projectId);
//        executeMapDate(channelId, projectId, socketSession);
//        //查询当前项目的所有扬尘设备的实时数据
//        SpringUtils.getApplicationContext().publishEvent(new ClimateDeviceWebOpenEvent(this, projectId));
//        HashMap<String, String> map = new HashMap<>();
//        map.put("message", "连接成功");
//        sendMessage(socketSession, JsonUtils.toJson(map));
    }

    @OnMessage
    public void onMessage(Session socketSession, String message) {
        Channel channel = socketSession.channel();
        String channelId = channel.id().asShortText();
        if (StrUtil.isBlank(channelId)) {
            return;
        }
//        JsonNode json = JsonUtils.toJsonNode(message);
//        if (ObjectUtil.isNull(json)) {
//            return;
//        }
//        JsonNode type = json.get("message");
//        if (ObjectUtil.isNull(type)) {
//            return;
//        }
//        String s = type.textValue();
//        switch (s){
//            case MESSAGE_ONLINE:
//                JsonNode projectIdNode = json.get("projectId");
//                int projectIdInt = projectIdNode.intValue();
//                Long projectId = null;
//                try {
//                    projectId  = Long.parseLong(projectIdInt+"");
//                }catch (Exception e){
//                    HashMap<String, String> map = new HashMap<>();
//                    map.put("message", "projectId必须为数字");
//                    sendMessage(socketSession, JsonUtils.toJson(map));
//                }
////                executeOnline(socketSession, projectId);
//                break;
//            default:
//        }

    }

    @OnClose
    public void onClose(Session socketSession){
        log.info("one connection closed");
        Channel channel = socketSession.channel();
        String channelId = channel.id().asShortText();
        if(StrUtil.isNotBlank(channelId)){

        }
    }

    @OnError
    public void onError(Session socketSession, Throwable throwable) {
        Channel channel = socketSession.channel();
        String channelId = channel.id().asShortText();
        if(StrUtil.isNotBlank(channelId)){

        }
        throwable.printStackTrace();
    }
}
