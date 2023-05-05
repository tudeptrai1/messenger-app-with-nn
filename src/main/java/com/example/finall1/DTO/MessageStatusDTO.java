package com.example.finall1.DTO;

public class MessageStatusDTO
{
    int receiver_id;
    int message_id;
    boolean is_read;
    public MessageStatusDTO(int receiver_id, int message_id,boolean is_read) {
        this.receiver_id = receiver_id;
        this.message_id = message_id;
        this.is_read = is_read;
    }public MessageStatusDTO(int receiver_id, int message_id) {
        this.receiver_id = receiver_id;
        this.message_id = message_id;

    }

    public int getReceiverID() {
        return receiver_id;
    }

    public void setReceiverID(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getMessageID() {
        return message_id;
    }

    public void setMessageID(int message_id) {
        this.message_id = message_id;
    }
    public boolean getMessStatus() {
             return is_read;
    }  public void setMessStatus(boolean is_read) {
             this.is_read = is_read;
    }

    @Override
    public String toString() {
        return "MessageStatusDTO{" + "receiver_id=" + receiver_id + ", message_id=" + message_id + ", is_read=" + is_read + '}';
    }
}
