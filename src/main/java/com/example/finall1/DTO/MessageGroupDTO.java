
package com.example.finall1.DTO;


import java.sql.Date;

public class MessageGroupDTO {
    int id;
    int user_id;
    int group_id;
    Date created_at;
    String content;


    public MessageGroupDTO(int id, int user_id, int group_id, Date created_at, String content, boolean isURL) {
        this.id = id;
        this.user_id = user_id;
        this.group_id = group_id;
        this.created_at = created_at;
        this.content = content;

    }
    
    public MessageGroupDTO(int user_id, int group_id, Date created_at, String content, boolean isURL) {
        
        this.user_id = user_id;
        this.group_id = group_id;
        this.created_at = created_at;
        this.content = content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public int getGroupID() {
        return group_id;
    }

    public void setGroupID(int group_id) {
        this.group_id = group_id;
    }

    public Date getTime() {
        return created_at;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    

    @Override
    public String toString() {
        return "MessageGroupDTO{" + "id=" + id + ", user_id=" + user_id + ", group_id=" + group_id + ", created_at=" + created_at + ", content=" + content + '}';
    }
}
