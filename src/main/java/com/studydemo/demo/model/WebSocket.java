package com.studydemo.demo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/9 11:26
 */
@Component("websocket")
//定义webSocket服务器端，它的功能主要将目前的类定义成一个websocket服务器端。
//注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/websocket/{username}")
@Slf4j
public class WebSocket {
    private Session session;

    private String username;
    private static Integer userNumber = 0;
    private static CopyOnWriteArrayList<WebSocket> webSocketSet = new CopyOnWriteArrayList<>();

    /**
     * @Description 新增一条连接
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/9 11:38
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username) throws IOException {
        this.session = session;
        webSocketSet.add(this);
        this.username = username;
        userNumber++;
        Set<String> userLists = new TreeSet<>();
        for(WebSocket webSocket: webSocketSet){
            userLists.add(webSocket.username);
        }
        //将所有信息包装好传到客户端(所有用户)
        Map<String,Object> map1 = new HashMap<>();
        //包装用户列表
        map1.put("onlineUsers",userLists);
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
        map1.put("messageType",1);
        //返回用户名
        map1.put("username",username);
        map1.put("number",userNumber);
        sendMessageAll(JSON.toJSONString(map1),this.username);
        log.info("【websocket消息】有新的链接,总数：{}",webSocketSet.size());

        //更新在线人数(给所有人)
        Map<String,Object> map2 = new HashMap<>();
        map2.put("messageType",3);
        map2.put("onlineUsers",userLists);
        map2.put("number",userNumber);
        sendMessageAll(JSON.toJSONString(map2),this.username);
    }

    /**
     * @Description 断开当前socket
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/9 11:37
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);

        userNumber--;
        Map<String,Object> map1 = new HashMap<>();
        map1.put("messageType",2);
        map1.put("onlineUsers",webSocketSet);
        map1.put("username",this.username);
        map1.put("number",userNumber);
        sendMessageAll(JSON.toJSONString(map1),this.username);
        log.info("【websocket消息】链接断开,总数：{}",webSocketSet.size());

        Map<String,Object> map2 = new HashMap<>();
        Set<String> userLists = new TreeSet<>();
        for (WebSocket webSocket : webSocketSet){
            userLists.add(webSocket.username);
        }
        map2.put("messageType",3);
        map2.put("onlineUsers",userLists);
        map2.put("number",userNumber);
        sendMessageAll(JSON.toJSONString(map2),this.username);
        log.info("【websocket消息】连接断开,总数:{}",webSocketSet.size());
    }

    /**
     * @Description 接收前端消息
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/9 11:37
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("【websocket消息】收到客户端消息：{}",message);
        JSONObject jsonObject = JSON.parseObject(message);
        String textMessage = jsonObject.getString("message");
        String username = jsonObject.getString("username");
        String type = jsonObject.getString("type");
        String toUsername = jsonObject.getString("tousername");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("messageType",4);
        map3.put("onlineUsers",webSocketSet);
        map3.put("username",username);
        map3.put("number",userNumber);
        map3.put("textMessage",textMessage);
        if(type.equals("群发")){
            sendMessageAll(JSON.toJSONString(map3),this.username);
        }else {
            sendMessageTo(JSON.toJSONString(map3),toUsername);
            Map<String,Object> map4 = new HashMap<>();
            map4.put("messageType",4);
            map4.put("onlineUsers",webSocketSet);
            map4.put("username",username);
            map4.put("number",userNumber);
            map4.put("textMessage",textMessage);
            sendMessageTo(JSON.toJSONString(map3),username);
        }
    }

    /**
     * @Description 主动向客户端发送消息
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/9 11:36
     */
    public static void sendMessage(String message){
        for (WebSocket socket:webSocketSet){
            log.info("【websocket消息】广播消息,message:{}",message);
            try {
                socket.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
                throw new BaseException(BaseErrorEnum.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public void sendMessageAll(String message,String fromUsername) throws IOException {
        for (WebSocket socket:webSocketSet){
            socket.session.getBasicRemote().sendText(message);
        }
    }

    public void sendMessageTo(String message,String toUserName) throws IOException{
        for (WebSocket socket:webSocketSet){
            if(socket.username.equals(toUserName)){
                socket.session.getBasicRemote().sendText(message);
                log.info("【发送消息】:",this.username+"向"+toUserName+"发送消息："+message);
                break;
            }
        }
    }

}
