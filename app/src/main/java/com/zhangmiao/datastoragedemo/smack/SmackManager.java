package com.zhangmiao.datastoragedemo.smack;

import android.content.Context;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.OfflineMessageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zsz on 2018/1/11.
 */

public class SmackManager {
    public static XMPPConnection connection;
    private static Roster roster;
    private static List<AssistantInfo> assistantInfoList;
    private static ChatManager chatManager;
    public static Chat chat;
    public static String User;
//    private static CommunicationCacheDao cacheDao;
    private static Context ct;
    private static ConnectionConfiguration config;
    private static ActivityCallBridge callBridge;
    /**
     * 连接服务器
     */
    private static void connectionSmackServer(){
        try{
            if(null==connection || !connection.isAuthenticated()){
                config=new ConnectionConfiguration("122.112.3.4", Integer.parseInt("5222"));
               config.setReconnectionAllowed(true);//断线自动重连
                config.setSendPresence(false);//状态为离线状态，目的为了取离线消息
                config.setSASLAuthenticationEnabled(false);//是否启用安全验证
                config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                connection=new XMPPConnection(config);
                connection.connect();//连接服务器
            }
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
    /**
     * Smack登录
     * @Param @param name
     * @Param @param password
     */
    public static void smackLogin(final String name,final String password,Context context) {
        ct = context;
        new Thread() {
            public void run() {
                try {
                    connectionSmackServer();
                    try {
                        connection.login("zhanghui", "111111");
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                    //获取离线消息
                    OfflineMessageManager offlineMessageManager = new OfflineMessageManager(connection);
                    try {
                        Iterator<Message> it = offlineMessageManager.getMessages();
                        Map<String, ArrayList<Message>> offlineMsgs = new HashMap<String, ArrayList<Message>>();
                        while (it.hasNext()) {
                            org.jivesoftware.smack.packet.Message message = it.next();
                            String fromUser = message.getFrom().split("/")[0];
                            if (offlineMsgs.containsKey(fromUser)) {
                                offlineMsgs.get(fromUser).add(message);
                            } else {
                                ArrayList<Message> temp = new ArrayList<Message>();
                                temp.add(message);
                                offlineMsgs.put(fromUser, temp);
                            }
                        }
                        //在这里处理离线消息集合
                        Set<String> keys = offlineMsgs.keySet();
                        Iterator<String> offIt = keys.iterator();
                        while (offIt.hasNext()) {
                            String key = offIt.next();
                            ArrayList<Message> ms = offlineMsgs.get(key);

                            for (int i = 0; i < ms.size(); i++) {

                                MessageContent content = new MessageContent();
                                String str = ms.get(i).getBody();
                                if (str == null) {
                                    str = "";
                                }
                                content.setMessage_content(ms.get(i).getBody());
                                content.setMessage_reception_Jid(ms.get(i).getTo().split("/")[0]);
                                content.setMessage_send_jid(ms.get(i).getFrom().split("/")[0]);
                                content.setMessage_type(0);
//								cacheDao.add(content);
                            }
                        }

                        offlineMessageManager.deleteMessages();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                    //设置状态为在线
                    Presence presence = new Presence(Presence.Type.available);
                    connection.sendPacket(presence);
                    //注册接受消息
                    receiveMessage(connection);
                } catch (Exception e) {
                    System.out.println("登陆失败");
                    e.printStackTrace();
                }
            }

        }.start();
    }

    /**
     * 获取所有的助理信息
     * @param
     */
    public static List<AssistantInfo> getFriendInfoList(){
        assistantInfoList = new ArrayList<AssistantInfo>();
        try {
            if(connection.isAuthenticated()){
                roster = connection.getRoster();
                if (roster != null) {
                    Collection<RosterGroup> entriesGroup = roster.getGroups();
                    Collection<RosterEntry> entries = roster.getEntries();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (RosterGroup rosterGroup : entriesGroup) {
                        System.out.println(rosterGroup.getEntryCount());
                    }
                    for(RosterEntry entry:entries){
                        //获取好友在线状态
                        Presence presence = roster.getPresence(entry.getUser());
                        AssistantInfo assistantInfo = new AssistantInfo();
                        assistantInfo.setName(entry.getName());
                        assistantInfo.setUser(entry.getUser());
                        assistantInfo.setType(entry.getType());
                        assistantInfo.setSize(entry.getGroups().size());
                        assistantInfo.setStatus(presence.getStatus());
                        assistantInfo.setFrom(presence.getFrom());
                        if(presence.isAvailable()==true){//判断好友是否在线
                            //相应的逻辑操作
                            assistantInfo.setAvailable(true);
                        } else {
                            assistantInfo.setAvailable(false);
                        }
                        System.out.println(assistantInfo.toString());
                        assistantInfoList.add(assistantInfo);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assistantInfoList;
    }

    /**
     * 接收消息
     * @param connection
     */

    private static void receiveMessage(XMPPConnection connection) {
        chatManager = connection.getChatManager();
        //		final String userId = connection.getUser().split("/")[0];
        chatManager.addChatListener(new ChatManagerListener() {

            public void chatCreated(Chat chat, boolean arg1) {
                chat.addMessageListener(new MessageListener() {//通过添加一个messagelitener 来接收消息
                    public void processMessage(Chat arg0, Message message) {
                        if(CacheInformation.communicationLogo){
                            //启动广播发送消息提醒

                        }
                        //使用回调接口实现实时显示接受的消息
                        callBridge = ActivityCallBridge.getInstance();
                        callBridge.invokeMethod(message);
                    }
                });
            }
        });
    }
    /**
     * sendMessage
     * 将消息发送给服务器
     */

    public static void sendMessage(final String msg, final String msgto){
        //初始化发送消息，chatManager在前文初始化过

        final String userId = connection.getUser().split("/")[0];

        chatManager = connection.getChatManager();

        chat = chatManager.createChat(msgto, null);
        new Thread() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    message.setLanguage("UTF-8");
                    message.setTo(msgto);
                    message.setFrom(userId);
                    message.setBody(msg);
                    //消息发送出去
                    chat.sendMessage(message);
                    MessageContent content = new MessageContent();
//					content.setMessage_time(CommonUtil.getStringDate());
                    content.setMessage_content(msg);
                    content.setMessage_reception_Jid(msgto);
                    content.setMessage_send_jid(userId);
                    content.setMessage_type(1);
//					cacheDao.add(content);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    }



