package com.zhangmiao.datastoragedemo.smack;


public class MessageContent {

	private String message_time;//����ʱ��
	private int message_type;//��������
	private String message_reception_Jid;//JId
	private String message_send_jid;//������Ϣ����
	private String message_content;//��������
	private int delivery_status;//����״̬
	private String packet_id;
	public String getMessage_time() {
		return message_time;
	}
	public void setMessage_time(String message_time) {
		this.message_time = message_time;
	}
	public int getMessage_type() {
		return message_type;
	}
	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}
	public String getMessage_reception_Jid() {
		return message_reception_Jid;
	}
	public void setMessage_reception_Jid(String message_reception_Jid) {
		this.message_reception_Jid = message_reception_Jid;
	}
	public String getMessage_send_jid() {
		return message_send_jid;
	}
	public void setMessage_send_jid(String message_send_jid) {
		this.message_send_jid = message_send_jid;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public int getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(int delivery_status) {
		this.delivery_status = delivery_status;
	}
	public String getPacket_id() {
		return packet_id;
	}
	public void setPacket_id(String packet_id) {
		this.packet_id = packet_id;
	}

	
	
}
