package com.zhangmiao.datastoragedemo.smack;

import android.util.Log;

import com.zhangmiao.datastoragedemo.sevice.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.parts.Localpart;
/**
 * Created by zsz on 2018/1/11.
 */

public class SmackManager {
    public static final String server = "v.izengshi.com";

    private static XMPPTCPConnection connection;
    private static ChatManager chatManager;

    /**
     * 获取一个单利的 XMPPTCPConnection 对象
     *
     * @return
     */
    public synchronized static void getConnection(final String user, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (connection == null) {
                    connection = OenXMPPConnection(user,password);
                    getChatManager();
                    recieveMessage();
                }
                Log.d("TAG", "是否连接" + connection.isConnected());
                //return connection;
            }
        }).start();

    }

    /**
     * 建立连接
     * @return XMPPTCPConnection
     */
    private static XMPPTCPConnection OenXMPPConnection(String user, String password) {
        XMPPTCPConnection conn1 = null;
        try {
            if (!isConnected()) {
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setUsernameAndPassword(user,password)
                        .setSendPresence(true)//上线通知服务
                        .setPort(5222)
                        .setResource("android")
                        .setXmppDomain(server)
                        .setDebuggerEnabled(true)
                        .setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled)
                        .build();
                conn1 = new XMPPTCPConnection(config);
                conn1.addConnectionListener(new ConnectionListener() {
                    @Override
                    public void connected(XMPPConnection connection) {
                        Log.d("tag","建立连接");

                    }

                    @Override
                    public void authenticated(XMPPConnection connection, boolean resumed) {

                    }

                    @Override
                    public void connectionClosed() {

                    }

                    @Override
                    public void connectionClosedOnError(Exception e) {

                    }

                    @Override
                    public void reconnectionSuccessful() {

                    }

                    @Override
                    public void reconnectingIn(int seconds) {

                    }

                    @Override
                    public void reconnectionFailed(Exception e) {

                    }
                });
                conn1.connect().login();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return conn1;
    }
         private static void  recieveMessage(){
           chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, org.jivesoftware.smack.chat2.Chat chat) {
             //from就是消息的发送方jid,message即发送的消息对象，chat就是单聊对象
                //获取from发送方的账号，转换成String即可
                Log.d("text",from.toString());
                //消息内容
                Log.d("text",message.getBody());



            }
        });
}
    /**
     * 关闭连接
     */
    public static void closeConnection() {
        if (isConnected()) {
            connection.disconnect();
            connection = null;
        }
    }


    public static boolean register(String user, String password) {
        if (isConnected()) {
            try {
                AccountManager accountManager = AccountManager.getInstance(connection);
                accountManager.createAccount(Localpart.from(user), password);
                accountManager.sensitiveOperationOverInsecureConnection(true); //安全认证
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * 获取ChatManager对象
     * @return
     */
    public static ChatManager getChatManager() {
        if (chatManager == null & isConnected()) {
            chatManager = ChatManager.getInstanceFor(connection);
        }
        return chatManager;
    }

    private static boolean isConnected() {
        return connection != null && connection.isConnected();
    }

}
