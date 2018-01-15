package com.zhangmiao.datastoragedemo.smack;

import org.jivesoftware.smack.packet.Message;

public class ActivityCallBridge {
	static ActivityCallBridge callBridge;

    private OnMethodCallback mCallback;

    private ActivityCallBridge() {

    }

    public static ActivityCallBridge getInstance() {
        if (callBridge == null) {
        	callBridge = new ActivityCallBridge();
        }
        return callBridge;
    }

    public void invokeMethod(Message message) {
        if (mCallback != null) {
            mCallback.doMethod(message);
        }
    }

    public void setOnMethodCallback(OnMethodCallback callback) {
        mCallback = callback;
    }

    public static interface OnMethodCallback {
        public void doMethod(Message message);
    }
}
