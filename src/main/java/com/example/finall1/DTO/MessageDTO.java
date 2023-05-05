
package com.example.finall1.DTO;


import java.sql.Date;

public class MessageDTO {
    int id;
    int sender_id;
    int receiver_id;
    Date created_at;
    String content;


    public MessageDTO(int id, int sender_id, int receiver_id, Date created_at, String content) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.created_at = created_at;
        this.content = content;

    }

    public MessageDTO(int sender_id, int receiver_id, Date created_at, String content) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.created_at = created_at;
        this.content = content;

    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderID() {
        return sender_id;
    }

    public void setSenderID(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiverID() {
        return receiver_id;
    }

    public void setReceiverID(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Date getTime() {
        return created_at;
    }

    public void setTime(Date created_at) {
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageDTO{" + "id=" + id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", created_at=" + created_at + ", content=" + content   + '}';
    }
    
    
}
