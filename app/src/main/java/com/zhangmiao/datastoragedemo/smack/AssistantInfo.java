package com.zhangmiao.datastoragedemo.smack;


import org.jivesoftware.smack.roster.packet.RosterPacket;

public class AssistantInfo {
     private String name;
     private String user;
     private RosterPacket.ItemType type;
     private int size;
     private String status;
     private String from;
     private boolean isAvailable;
     
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
//	public ItemType getType() {
//		return type;
//	}
	public void setType(RosterPacket.ItemType type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", user=" + user + ", type=" + type
				+ ", size=" + size + ", status=" + status + ", from=" + from
				+ ", isAvailable=" + isAvailable + "]";
	}
     
     
}
